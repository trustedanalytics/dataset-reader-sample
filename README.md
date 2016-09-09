[![Build Status](https://travis-ci.org/trustedanalytics/dataset-reader-sample.svg)](https://travis-ci.org/trustedanalytics/dataset-reader-sample)
[![Dependency Status](https://www.versioneye.com/user/projects/57236598ba37ce00464e02ef/badge.svg?style=flat)](https://www.versioneye.com/user/projects/57236598ba37ce00464e02ef)

# Dataset Reader: A Sample Application for Reading a Dataset from HDFS
This project contains a sample application that reads a dataset from HDFS and presents it in a graphical form to the user. The figure below shows the data flow for this sample.

![](docs/dataset-reader.png)

## Implementation summary
* The dataset is uploaded through the TAP **Data catalog** into the platform and stored on HDFS.
* The data scientist performs analysis on it using the Analytics Toolkit. The resulting file is also stored on HDFS.
* The application developer uploads the dataset-reader application into the platform and binds it with the file produced by the data scientist.
* The dataset-reader application presents the dataset visually as a set of charts.

## Preparing data
The best way to learn about the data analytics capabilities of the platform is to work through [Workshop: Performing Analytics on Your Data](https://community.trustedanalytics.org/docs/DOC-1043).

However, if you are mostly interested in using the application to visualize data, you can follow the steps below to make use of a pre-built dataset (instead of working through the workshop mentioned above).

#### Using pre-built dataset

1. Clone this GitHub repository.
1. In the TAP console, navigate to **Data catalog > Submit transfer**.
1. Choose **Local path**, then navigate to the local file and select it.
1. Select the file to upload (sample dataset can be found here: [data/nf-data-application.csv](data/nf-data-application.csv)).
    * Or you can select **Link** and paste the link to the raw file on GitHub (which is,  [nf-data-application.csv](https://raw.githubusercontent.com/trustedanalytics/dataset-reader-sample/master/data/nf-data-application.csv)).
1. Enter a title in the **Title** field.
1. Click the **Upload** button.
1. When the transfer finishes, your new dataset will be visible in **Data catalog > Data sets**.
1. To acquire the link to the file on HDFS, click on the name of your dataset in **Data catalog > Data sets** and copy the value of the **targetUri** property. 

## Using the app to visualize the data

To learn about the data visualization capabilities of the platform, work through [Workshop: Visualizing Your Data with an App](https://community.trustedanalytics.org/docs/DOC-1044).

>**Note:** If you made use of the pre-built dataset (instead of building your own), you can still work through the Visualization Workshop. But remember to supply the title you chose in step 5 above when you get to Visualization Workshop step 8 (Update the environment and set the dataset link).

Useful links:

1. Training dataset: [nf-hour.csv](https://s3-us-west-2.amazonaws.com/analytics-tool-kit/public/datasets/latest/nf-hour.csv)
1. Jupyter notebook: [Netflow_Demo.pynb](src/analytics/Netflow_Demo.ipynb)
