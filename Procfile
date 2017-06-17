web: node --optimize_for_size --max_old_space_size=460 --gc_interval=100 server.js; java -jar target/*.war --spring.profiles.active=prod,heroku --server.port=$PORT
