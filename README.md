## CI/CD Tools Practice Project

This small project showcases a few technologies used to create a CI/CD Pipeline.
- Git: Version-controlled the project
- Docker: Containerized the app
- Kubernetes: Deployments, Pods, Services, replicas, self-healing and label-based service discovery
- Ansible: Used to orchestrate/automate deployment dteps rather than running commands manually.
- CI/CD (GitHub Actions): Build -> Test -> Deploy

### Prerequisites
- Git
- Docker
- Local Kubernetes Cluster: e.g. Minikube
- kubectl
- Ansible

### Part 1 - Get the app to run manually using Kubernetes
In this mini project, we used minikube to set up a local kubernetes environment
```Bash
# Point local Docker CLI at minikube's internal Docker daemon
eval $(minikube docker-env)

# Build docker image
docker build -t app:latest .

# Run Kubernete's deployment.yaml
kubectl apply -f k8s/deployment.yaml

# Ensure that the last command was successfull
kubectl get pods # There should be 2 pods
```

One of Kubernetes core functionalities is its ability to self heal. If we kill one of the pods on purpose and run `kubectl get pods` straight away a new pod is already there.

### Part 2 - Expose the app as a Service
Since Kubernetes Pods aren't directly reachable by default, we need a Service
```bash
# Apply service.yaml
kubectl apply -f k8s/service.yaml

# Check the services
kubectl get services
```

### Part 3 - Use Ansible to automate Parts 1 and 2
```ansible-playbook -i inventori.ini deploy-playbook.yml```

### Part 4 - Tie everything into CI/CD pipeline 
Here we automate the deployment on push to github.

```yaml
 deploy:
    needs: build-and-test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    steps:
      - uses: actions/checkout@v4
      # In a real environment, this step would run against a real cluster:
      # - name: Deploy via Ansible
      #   run: ansible-playbook -i inventory.ini deploy-playbook.yml
      - name: Deploy placeholder
        run: echo "Would run Ansible playbook against real cluster here"
```
