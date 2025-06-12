# 🚀  Flask App Deployment on Kubernetes

This repository is part of the **AlgoTest DevOps interview assignment**. It demonstrates a complete CI/CD pipeline and infrastructure automation solution to deploy a containerized Python Flask application to a **Kubernetes cluster on DigitalOcean** using **Terraform** and **Jenkins**.

## ✅ Objective

The goal of this assignment is to showcase my ability to:
- Build a containerized Flask application using Docker
- Automate infrastructure provisioning on DigitalOcean using Terraform
- Deploy to Kubernetes using best practices (probes, resource limits)
- Build a CI/CD pipeline in Jenkins using **shared libraries**
- Apply industry-standard DevOps principles (modular, reusable, scalable)

## 🛠️ Tech Stack & Tools

| Component         | Technology Used                           |
|------------------|-------------------------------------------|
| Language          | Python (Flask)                            |
| Containerization  | Docker                                    |
| Orchestration     | Kubernetes (Managed on DigitalOcean)      |
| IaC               | Terraform                                 |
| CI/CD             | Jenkins (with shared library functions)   |
| Registry          | DigitalOcean Container Registry           |

## 📁 Project Structure

```
algotest-assignment/
├── README.md
├── Jenkinsfile
├── terraform/
│   ├── provider.tf
│   ├── main.tf
│   ├── outputs.tf
│   ├── variables.tf
│   └── modules/
│       ├── kubernetes/
│       └── registry/
├── flask-app/
│   ├── app.py
│   ├── Dockerfile
│   └── k8s/
│       ├── deployment.yaml
│       ├── service.yaml
│       └── ingress.yaml
├── jenkins/
│   └── shared_libs/
│       └── vars/
│           ├── buildImage.groovy
│           ├── pushToRegistry.groovy
│           └── deployToK8s.groovy
```

## 🔧 Infrastructure Provisioning – Terraform

```bash
cd terraform
terraform init
terraform apply
```

Ensure that you export your DigitalOcean token before running:

```bash
export DIGITALOCEAN_TOKEN="your_token"
```

## 🐳 Docker Image – Flask App

```bash
cd flask-app
docker build -t flask-app .
docker tag flask-app registry.digitalocean.com/<registry-name>/flask-app:latest
docker push registry.digitalocean.com/<registry-name>/flask-app:latest
```

## ☸️ Kubernetes Deployment

```bash
kubectl apply -f flask-app/k8s/deployment.yaml
kubectl apply -f flask-app/k8s/service.yaml
kubectl apply -f flask-app/k8s/ingress.yaml
```

## 🔁 Jenkins CI/CD Pipeline

Jenkins automates:
1. Code checkout
2. Docker image build
3. Push to DigitalOcean registry
4. Deploy to Kubernetes

### 🔗 Shared Library Functions

- `buildImage.groovy`
- `pushToRegistry.groovy`
- `deployToK8s.groovy`

Pipeline is defined in the root `Jenkinsfile`.

## 🌍 Application Access

```bash
kubectl get service flask-app
```

The app listens on port `5001`, and `/health` is used by probes.

## 📊 Highlights

- ✅ Clean modular directory structure
- ✅ Parameterized Terraform modules
- ✅ Declarative Jenkins pipeline with shared libraries
- ✅ K8s best practices (resource limits, probes)
- ✅ Fully reproducible CI/CD workflow
- ✅ End-to-end DevOps automation

## 🧠 Future Enhancements

- Add domain + TLS with Let's Encrypt + Ingress
- Add monitoring with Prometheus & Grafana
- Add Helm for app deployment
- Use GitHub Actions or CircleCI as alternate CI/CD solutions

## 👨‍💻 Author

**Akash Deep Yadav**  
DevOps & Cloud Engineer  
GitHub: [@akashdeepyadav](https://github.com/akashdeepyadav)

## 📄 License

This project is submitted as part of a private interview assignment and is not licensed for commercial reuse.
