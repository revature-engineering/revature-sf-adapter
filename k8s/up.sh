# variables
APP=sf-auth
#ENVIRONMENT=production
ENVIRONMENT=staging


# services
kubectl apply -f services/$APP.yaml --namespace $ENVIRONMENT

# deployments
kubectl apply -f deployments/$APP.yaml --namespace $ENVIRONMENT

# ingress - don't need to expose
#kubectl apply -f ingress/$APP-$ENVIRONMENT.yaml

