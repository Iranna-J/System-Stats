# System-Stats

#### System Stats is a web-based application that provides real-time system information such as CPU usage, ram usage, swap memory usage, and core statistics. The backend is built using Java, and the frontend is built with React.

## Features
#### Backend: Provides system information using Java and relevant APIs.
#### Frontend: Displays system stats on a dashboard in a user-friendly interface built using React.

## How to [build](./scripts/build.bat)
Run from project root
```
./script/build.bat
```

## How to [run](./scripts/run.bat).
```
./script/run.bat
```

## How to attach another [terminal](./scripts/exec.bat)
```
./script/exec.bat
```

## How to [kill](./scripts/kill.bat) the docker
```
./script/kill.bat
```

## How it Works
### 1.Backend:

* The backend is a Java application that retrieves system information (CPU usage, Swap memory usage,  RAM Usage, and Core Statistics) using Java libraries.
* It exposes an API that the frontend consumes to display the stats in real-time.

### 2.Frontend:

* The frontend is built with React.js, and it uses Axios to request data from the backend.
* It then displays the collected stats in a user-friendly interface.

### 3.Docker:

* Both backend and frontend are packaged into Docker