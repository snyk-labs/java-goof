## Java Goof

This is a collection of Java demo apps that are vulnerable in different ways.

It's divided into modules, each one having its own README:

* [Todolist Goof](todolist-goof/README.md)
* [Log4Shell Goof](log4shell-goof/README.md)

---
## Kubernetes based Todolist + Log4Shell exploit
To deploy Todolist on Kubernetes along with the needed ldap backend for exploiting the Log4shell
vulnerability:

### Prerequisites
1. A kubernetes cluster where you have permissions to create namespaces, deployments and services
2. The `kubectl` client and credenials configuration
3. Docker Desktop or docker-ce (for building and pushing images)
4. A DockerHub account

### Quickstart
Assuming you have your kubernetes cluster up and ready, from the top level of this repo you can run `./k8s-quickstart.sh` which will do the following:
1. Builds todolist-goof image and pushes it to Docker Hub. _(see below for account/tagging info)_
2. Deploys the todolist to the `default` namespace in your kubernetes cluster along with a LoadBalancer type service
3. Builds the log4shell-server image and pushes to Docker Hub. _(see below for account/tagging info)_
4. Deploys the log4shell-server and a pair of ClusterIP type services into a new namespace named `darkweb` in your Kubernetes cluster.

### Accessing the application
Once complete, run `kubectl get svc` and note the IP Address or hostname of the `goof` service.

You should be able to open a browser to http://{svc-ip-addr}/todolist and see the app

#### EKS cluster notes
* In order to perform NetworkPolicy egress examples, you will need to deploy the Calico CNI plugin as EKS does not implement NetworkPolicy by default.
  The `eks-calico.sh` script in `todolist-goof/k8s` will deploy this for you. (that script is sym-linked to the top level here too)
* You should log into the AWS console and change inbound access for the good service's ELB to only allow your home IP, otherwise you *will* have audience members trying to mess with it.

#### Docker Desktop Kubernetes notes
* Docker Desktop automatically serves the goof service loadblancer external IP to your workstation's localhost so the app will be available at http://localhost/todolist
* Docker Desktop Kubernetes CNI does not implement Network Policy so you will not be able to demonstrate any mitigation techniques that use that.

#### Kind (Kubernetes on Docker) notes
* Kind's default CNI does not currently support Network Policy so you should deploy your own using the instructions on their website.
* If running Kind on top of Docker Desktop, you will need to run a port-forward to access the app.  For example, use something like this: `kubectl port-forward service/goof 8000:80` and then access it via browser at http://localhost:8000/todolist

### Quick cleanup
Run the `/.k8s-quickstop.sh` script at the top level of this repo which will do the following:
1. Deletes the todolist deployment and associated service in the `default` namespace
2. Deletes the log4shell deployment and associated services in the `darkweb` namespace and deltes the namespace as well
**Note:** This will not delete any additional objects you may have deployed such as NetworkPolicies.

It is up to you to shut down your Kubernetes cluster as appropriate.
