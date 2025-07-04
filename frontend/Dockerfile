# --------------------------
# 1️⃣ Build Stage
# --------------------------
FROM node:20-alpine AS build

WORKDIR /app

# Copy package.json (and lock file if it exists)
COPY package*.json ./
RUN npm install

COPY . .

RUN npm run build

# --------------------------
# 2️⃣ Production Stage
# --------------------------
FROM nginx:alpine

# Optional: custom NGINX config for React Router fallback
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built static files from build stage
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
