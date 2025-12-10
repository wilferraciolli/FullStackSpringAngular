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
### Angular animations
npm install @angular/animations --legacy-peer-deps

### Material
ng add @angular/material

### ngx signals store
npm install @ngrx/signals

### Prime NG for the org chart (Replaced by Angular material)
This library will be used to display the org chart, it needs installing the dependency plus adding the styles manually
Eg
Open styles.scss add these imports
```scss
    /* PrimeIcons (Optional, if you use their icons) */
    @import "primeicons/primeicons.css";
```
Also PrimeNG needs Angular animations to be enabled. Within the app.config.ts add the provider function [lus the theme provider]
```ts
import {provideAnimations} from '@angular/platform-browser/animations'; // <--- Import this

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(), // <--- Add this provider
    // NEW: Configure PrimeNG Theme here
    providePrimeNG({
      theme: {
        preset: Aura,
        options: {
          darkModeSelector: false || 'none' // Optional: Disable dark mode if you want light only
        }
      }
    })
  ]
};
```
npm install primeng primeicons --legacy-peer-deps
npm install @primeng/themes --legacy-peer-deps
