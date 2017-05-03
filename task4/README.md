# Environment setup

### Graphite:
 - sudo apt-get update && sudo apt-get upgrade
 - sudo apt-get install build-essential graphite-web graphite-carbon python-dev apache2 libapache2-mod-wsgi libpq-dev python-psycopg2
 - /etc/carbon/storage-schemas.conf -> add a new pattern "test" for example
 - sudo cp /usr/share/doc/graphite-carbon/examples/storage-aggregation.conf.example /etc/carbon/storage-aggregation.conf
 - /etc/default/graphite-carbon -> enable it
 - sudo service carbon-cache start
 
### PostgreSQL:
 - sudo apt-get install postgresql
 - sudo su - postgres
 - createuser sparkhomenko --pwprompt
 - createdb -O graphite graphite
 - createdb -O graphite grafana
 - su - sparkhomenko
 
### Graphite Configuration:
 - /etc/graphite/local_settings.py:
   - in DATABASES section (name: graphite, engine: django.db.backends.postgresql_psycopg2, port: '')
   - in the end (USE_REMOTE_USER_AUTHENTICATION = True, TIME_ZONE = 'Your/Timezone', SECRET_KEY = 'generate it')
 - sudo graphite-manage syncdb (didn't work), used:
   - python /usr/lib/python2.7/dist-packages/graphite/manage.py migrate auth
   - python /usr/lib/python2.7/dist-packages/graphite/manage.py migrate
   
### Apache:
 - sudo cp /usr/share/graphite-web/apache2-graphite.conf /etc/apache2/sites-available
 - /etc/apache2/sites-available/apache2-graphite.conf -> port is 8085
 - /etc/apache2/ports.conf -> Listen 8085
 - sudo a2dissite 000-default
 - sudo a2ensite apache2-graphite
 - sudo service apache2 reload
 
### Sample Data:
 - for i in 4 6 8 16 2; do echo "test.count $i &#96;date +%s&#96;" | nc -q0 127.0.0.1 2003; sleep 6; done
 
### Grafana:
 - echo 'deb https://packagecloud.io/grafana/stable/debian/ wheezy main' |  sudo tee -a /etc/apt/sources.list
 - curl https://packagecloud.io/gpg.key | sudo apt-key add -
 - sudo apt-get update && sudo apt-get install grafana
 - /etc/grafana/grafana.ini -> database section (type: postgres, host: 127.0.0.1:5432, name: grafana, user: sparkhomenko, password: dbpassword)
 - /etc/grafana/grafana.ini -> server section (uncomment: protocol, http_addr, http_port, domain: localhost, enforce_domain: true, root_url)
 - /etc/grafana/grafana.ini -> security section (uncomment: admin_user, admin_password, secret_key)
 - sudo a2enmod proxy proxy_http xml2enc
 - create file -> /etc/apache2/sites-available/apache2-grafana.conf:
  ```
  <VirtualHost *:8086>
 	ProxyPreserveHost On
 	ProxyPass / http://127.0.0.1:3000/
 	ProxyPassReverse / http://127.0.0.1:3000/
 	ServerName localhost
  </VirtualHost>
  ```
 - sudo a2ensite apache2-grafana
 - sudo update-rc.d grafana-server defaults 95 10
 - sudo service grafana-server start
 - sudo service apache2 restart
