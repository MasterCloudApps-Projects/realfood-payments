# Realfood-Payments

Servicio de pagos del TFM del Master CloudApps de la URJC

### Autores
- Juan Antonio Ávila Catalán, [@juanaviladev](https://github.com/juanaviladev)
- Cristo Fernando López Cabañas, [@cristoflop](https://github.com/cristoflop)

Este servicio contiene toda la funcionalidad asociada a los pagos de la aplicación RealFood, esta aplicación consta
de los siguientes componentes:

- [GitHub - Realfood-Clients](https://github.com/MasterCloudApps-Projects/realfood-clients)
- [GitHub - Realfood-Restaurants](https://github.com/MasterCloudApps-Projects/realfood-restaurants)
- [GitHub - Realfood-Payments](https://github.com/MasterCloudApps-Projects/realfood-payments)
- [GitHub - Realfood-Shipping](https://github.com/MasterCloudApps-Projects/realfood-shipping)

Estos servicios se ha desarrollado siguiendo el estilo
de [Arquitectura Hexagonal](https://es.wikipedia.org/wiki/Arquitectura_hexagonal_(software))

Operaciones disponibles con token de inicio de sesion

        - GET       /api/balance                Registro
        - POST      /api/balance                Recargar saldo

Operación de Publicación/Suscripción que ejecuta el servicio:

        - [Publish] Payment response            Resolucion en el pago de un pedido

        - [Consume] Payment request             Intento de pago de un pedido
        - [Consume] Register request            Registro de un nuevo usuario
        - [Consume] Delete user request         Borrado de un usuario

Diagrama de clases del dominio de la aplicacion:

```mermaid
classDiagram
class Client
class ClientId
class Balance

Client *-- ClientId : -id
Client *-- Balance : -balance

class Payment
class PaymentId
class Quantity

Payment *-- PaymentId : -id
Payment *-- Quantity : -quantity
Payment o-- Client : -client
```

Ejemplo de diagrama de clases para el caso de uso UpdateBalance:

![alt text](https://github.com/MasterCloudApps-Projects/realfood-payments/blob/main/class-diagram-payments.png)

## Despliegue

### Docker

- Despliegue de recursos (Solo BD y broker de RabbitMq)

```
$ docker-compose -f realfood-deply/docker-compose.yml up --build
```

- Despliegue completo (Recursos y servicio de pagos)

```
$ docker-compose -f realfood-deply/docker-compose-prod.yml up --build
```

- Para observar que se han creado los contenedores:

```
$ docker ps
```

Software recomendado: [Docker desktop](https://www.docker.com/) / [Rancher desktop](https://rancherdesktop.io/)

### Kubernetes

En la carpeta de realfood-deployment están los manifiestos para desplegar los recursos y el servicio

- Arrancar el servicio de minikube

```
$ minikube start
```

- Arrancar broker de RabbitMQ

```
$ kubectl apply -f rabbitmq-pv.yaml

$ kubectl apply -f rabbitmq-pv-claim.yaml

$ kubectl apply -f rabbitmq-deployment.yaml

$ kubectl apply -f rabbitmq-service.yaml
```

- Arrancar BD de pagos

```
$ kubectl apply -f sqldbpayments-pv.yaml

$ kubectl apply -f sqldbpayments-pv-claim.yaml

$ kubectl apply -f sqldbpayments-deployment.yaml

$ kubectl apply -f sqldbpayments-service.yaml
```

- Arrancar Servicio de clientes

```
$ kubectl apply -f payments-deployment.yaml

$ kubectl apply -f payments-service.yaml
```

- Para observar que se han desplegado los servicios:

```
$ kubectl get deployments

$ kubectl get services
```

- Si se quiere levantar todo directamente:
```
$ kubectl apply -f .
```

Software recomendado: [k8sLens](https://k8slens.dev/)
