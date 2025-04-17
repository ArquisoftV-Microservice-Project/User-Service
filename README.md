# Users API

This service is written in Java with SpringBoot. It provides simple API to retrieve user data.

## 🚀 CI/CD Workflow

The `.github/workflows/build.yml` file defines a GitHub Action that performs the following steps:

1. **Checkout the code**: Retrieves the source code from the `main` branch.
2. **Authenticate with GCP**: Uses a service account (`GCP_SA_CREDENTIALS`) to authenticate with Google Cloud.
3. **Set up GCP SDK**: Installs and configures the `gcloud` CLI.
4. **Configure Docker with Artifact Registry**: Allows Docker to push images to GCP’s container registry.
5. **Get GKE credentials**: Connects to the GKE cluster to run `kubectl` commands.
6. **Set image tag**: Generates a unique image tag per commit using the branch name and timestamp.
7. **Build Docker image**: Builds and tags the Docker image with both the generated tag and `latest`.
8. **Push the image**: Pushes both `latest` and timestamped tags to GCP's Artifact Registry.
9. **Update deployment on GKE**: If a deployment named `frontend` exists, it updates the container image for `frontend-container`.

---

## 📁 Project Structure

```
.
├── Dockerfile
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── src
└── target
└── .github/workflows/   # GitHub Actions workflows
```

---

## 🛠️ Requirements

To make the workflow run successfully, you need to set up the following in your GitHub repository:

### 🔐 Secrets

- `GCP_SA_CREDENTIALS`: JSON with credentials for a service account that has permissions for:
  - Artifact Registry
  - GKE (get-credentials and set image)
- `GCP_PROJECT`: Google Cloud project ID
- `GKE_CLUSTER`: Name of your GKE cluster

### ⚙️ Variables

- `GCP_ZONE`: Zone where the cluster is located (e.g. `us-central1`)
- `REGISTRY`: Artifact Registry repository name (e.g. `my-registry`)
- `IMAGE_NAME`: Name of the image to be built (e.g. `frontend`)

---

## 🐳 Docker

This project includes a `Dockerfile` that packages the app for production. Make sure the Dockerfile is properly configured for your production environment.

---

## 🖥️ Local Development

- `GET /users` - list all users
- `GET /users/:username` - get a user by name

## Configuration

The service scans environment for variables:

- `JWT_SECRET` - secret value for JWT token processing. Must be the same amongst all components.
- `SERVER_PORT` - the port the service takes.

## Building

```
./mvnw clean install
```

## Running

```
JWT_SECRET=PRFT SERVER_PORT=8083 java -jar target/users-api-0.0.1-SNAPSHOT.jar
```

## Usage

In case you need to test this API, you can use it as follows:

```
 curl -X GET -H "Authorization: Bearer $token" http://127.0.0.1:8083/users/:username
```

where `$token` is the response you get from [Auth API](/auth-api).

## Dependencies

Here you can find the software required to run this microservice, as well as the version we have tested.
| Dependency | Version |
|-------------|----------|
| Java | openJDK8 |
