# ==============================
# STAGE 1: Build the Angular App
# ==============================
# Use Node 22 (Active LTS) for modern Angular
FROM node:22-alpine AS build

WORKDIR /app

# Copy package files first to leverage Docker cache
COPY package*.json ./

# Install dependencies strictly
RUN npm ci

# Copy the rest of the application code
COPY . .

# Build the application for production
RUN npm run build -- --configuration=production

# ==============================
# STAGE 2: Serve with Nginx
# ==============================
FROM nginx:alpine

# Remove default Nginx website
RUN rm -rf /usr/share/nginx/html/*

# Copy the build output from Stage 1 to Nginx html folder
# NOTE: Verify the path 'dist/app-name/browser' matches your project structure!
# If using older Angular versions (pre-v17), remove '/browser' from the end.
COPY --from=build /app/dist/org-chart-ui/browser /usr/share/nginx/html

# Copy our custom nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
