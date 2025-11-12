@ECHO OFF
SETLOCAL

ECHO = STEG 1 av 3: Startar Kubernetes-infrastruktur... ==

ECHO (Secrets, Lagring, Postgres, RabbitMQ)
ECHO.

kubectl apply -f k8s-infra/k8s-secret.yaml
kubectl apply -f k8s-infra/k8s-pvc.yaml
kubectl apply -f image-service/k8s/pvc.yaml
kubectl apply -f k8s-infra/k8s-postgres.yaml
kubectl apply -f k8s-infra/k8s-rabbitmq.yaml

ECHO.
ECHO ...Vantar 30 sekunder pa att databasen och RabbitMQ ska starta helt.
timeout /t 30
CLS
ECHO = STEG 2 av 3: Startar alla mikrotjanster i Kubernetes... ==

ECHO Startar patient-journal-service...
kubectl apply -f patient-journal-backend-microservices/k8s/k8s-deployment.yaml
kubectl apply -f patient-journal-backend-microservices/k8s/k8s-service.yaml

ECHO Startar user-auth-service...
kubectl apply -f user-auth-service/k8s/deployment.yaml
kubectl apply -f user-auth-service/k8s/service.yaml

ECHO Startar image-service...
kubectl apply -f image-service/k8s/deployment.yaml
kubectl apply -f image-service/k8s/service.yaml

ECHO Startar search-service (anvander deplyment.yaml med 'l')...
kubectl apply -f search-service/src/main/k8s/deplyment.yaml
kubectl apply -f search-service/src/main/k8s/service.yaml

ECHO Startar message-service...
kubectl apply -f message-service/k8s/message-deployment.yaml
kubectl apply -f message-service/k8s/message-service.yaml

ECHO.
ECHO ...Alla tjanster har applicerats. Vantar 5 sekunder...
timeout /t 5
CLS

ECHO STEG 3 av 3: Oppnar port-forwarding.

ECHO.
ECHO Oppnar 4 nya CMD-fonster for att gora tjansterna atkomliga.
ECHO Lat dessa fonster vara oppna sa lange du vill testa!
ECHO.

start "Auth Service (8081)" cmd /c "ECHO Ansluter till user-auth-service... && kubectl port-forward svc/user-auth-service 8081:8081"
start "Main Backend (8080)" cmd /c "ECHO Ansluter till patient-journal-service... && kubectl port-forward svc/patient-journal-service 8080:8080"
start "Search Service (8083)" cmd /c "ECHO Ansluter till search-service... && kubectl port-forward svc/search-service 8083:8083"
start "Image Service (3000)" cmd /c "ECHO Ansluter till image-service... && kubectl port-forward svc/image-service 3000:3000"

ECHO = KLART

ECHO.
ECHO Tjansterna ligger i dessa portar
ECHO - Auth:    http://localhost:8081
ECHO - Backend: http://localhost:8080
ECHO - Search:  http://localhost:8083
ECHO - Image:   http://localhost:3000
ECHO.
ECHO overvaka kommando: kubectl get pods -w
ECHO.
PAUSE
ENDLOCAL