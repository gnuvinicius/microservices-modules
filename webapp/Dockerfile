FROM node:20 as builder
WORKDIR /app
COPY . .
ENV API_HOST 192.168.0.220

RUN npm install
RUN npm run build

FROM nginx:alpine
COPY --from=builder /app/dist/webapp/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
COPY mime.types /etc/nginx/mime.types
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]