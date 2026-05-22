import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community';


// Registering AG grid so it can be used within the App
ModuleRegistry.registerModules([AllCommunityModule]);

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
