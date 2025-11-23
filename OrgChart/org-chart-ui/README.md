# OrgChartUI
Project used to represent an ORG chart.
This Web UI has its own docker file which uses the nginx.conf to run the docker image locally. 
There is also a parent docker compose file which will be used to build this image alongside the API.

### Build image on its own
```bash
    docker build -t org-chart-ui-app .
```
```bash
    docker run -d -p 4200:80 --name org-chart-ui-container org-chart-ui-app
```

## Dependencies
### Material
ng add @angular/material

### ngx signals store
npm install @ngrx/signals
