# Dataset Reader: A Sample Application for Reading a Dataset from HDFS

This project contains a sample application that is able to read a dataset from HDFS and present it in a graphical form to user.

Let's imagine flow as below:

![](docs/dataset-reader.png)

1. Dataset is uploaded through data catalog into the platform. The file is stored on the HDFS
2. Data scientist does some analysis on it using ATK. The result is also stored on HDFS
3. Application developer uploads the dataset-reader application into the platform and binds it with the file.
4. Dataset-reader presents the dataset in a nice form as a set of charts.

## Compilation and running

1. Clone this repository
  
  ```git clone https://github.com/trustedanalytics/dataset-reader-sample.git```
1. Compile it using Maven
  
  ```mvn compile```
1. (Optional) Run it locally passing path to the file

  ```FILE=<path_to_the_file> mvn spring-boot:run -Dspring.profiles.active=local```

## Pushing to the platform

1. Make Java package

  ```mvn package```
1. Login and set proper organization and space
  
  ```
cf api <platform API address>
cf login
cf target -o <organization name> -s <space name>
```
1. (Optional) Change the application name and host name if necessary in the ```manifest.yml```
  
  ```
  name: <your application name>
  host: <application host name>
  ```
  :information_source: For example, if you set host to "dataset-reader" and your platform URL is "example.com", the application will be hosted under 'dataset-reader.example.com' domain.
1. Push dataset-reader to the platform
  
  ```cf push```
1. Application will start but won't show anything 	because it doesn't know which file to serve. To fix that, pass the path to the file on HDFS as a environment variable called "FILE":
  
  ```
cf set-env <application name> FILE <path to file on HDFS>
cf restart <application name>
```
# Dataset Reader: A Sample Application for Reading a Dataset from HDFS

This project contains a sample application that is able to read a dataset from HDFS and present it in a graphical form to user.

Let's imagine flow as below:

![](docs/dataset-reader.png)

1. Dataset is uploaded through data catalog into the platform. The file is stored on the HDFS
2. Data scientist does some analysis on it using ATK. The result is also stored on HDFS
3. Application developer uploads the dataset-reader application into the platform and binds it with the file.
4. Dataset-reader presents the dataset in a nice form as a set of charts.

## Compilation and running

1. Clone this repository
  
  ```git clone https://github.com/trustedanalytics/dataset-reader-sample.git```
1. Compile it using Maven
  
  ```mvn compile```
1. (Optional) Run it locally passing path to the file

  ```FILE=<path_to_the_file> mvn spring-boot:run -Dspring.profiles.active=local```

## Pushing to the platform

1. Make Java package

  ```mvn package```
1. Login and set proper organization and space
  
  ```
cf api <platform API address>
cf login
cf target -o <organization name> -s <space name>
```
1. (Optional) Change the application name and host name if necessary in the ```manifest.yml```
  
  ```
  name: <your application name>
  host: <application host name>
  ```
  :information_source: For example, if you set host to "dataset-reader" and your platform URL is "example.com", the application will be hosted under 'dataset-reader.example.com' domain.
1. Push dataset-reader to the platform
  
  ```cf push```
1. Application will start but won't show anything 	because it doesn't know which file to serve. To fix that, pass the path to the file on HDFS as a environment variable called "FILE":
  
  ```
cf set-env <application name> FILE <path to file on HDFS>
cf restart <application name>
```
