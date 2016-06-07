#
# Copyright (c) 2016 Intel Corporation
#
# Licensed under the Apache License, Version 2.0 (the 'License');
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an 'AS IS' BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

"""
This scripts automates deployment of dataset-reader application
(creates required service instances, uploads dataset to HDFS, set proper env
and pushes application to Cloud Foundry using manifest file).
"""

import logging

from app_deployment_helpers import cf_cli
from app_deployment_helpers import cf_helpers

logging.basicConfig(level=logging.INFO)
LOGGER = logging.getLogger(__name__)

ARGS = cf_helpers.parse_args("dataset-reader-sample")
CF_INFO = cf_helpers.get_info(ARGS)
cf_cli.login(CF_INFO)

LOGGER.info('Creating hdfs service instance...')
cf_cli.create_service('hdfs', 'shared', 'hdfs-instance')

LOGGER.info('Creating kerberos service instance...')
cf_cli.create_service('kerberos', 'shared', 'kerberos-service')

PROJECT_DIR = cf_helpers.get_project_dir()

LOGGER.info('Creating artifact package...')
cf_helpers.prepare_package(work_dir=PROJECT_DIR)

LOGGER.info('Pushing application to Cloud Foundry...')
cf_helpers.push(options="{0} -n {0} --no-start".format(ARGS.app_name), work_dir=PROJECT_DIR)

LOGGER.info('Binding hdfs-instance...')
cf_cli.bind_service(ARGS.app_name, 'hdfs-instance')

LOGGER.info('Binding kerberos-service...')
cf_cli.bind_service(ARGS.app_name, 'kerberos-service')

LOGGER.info('Uploading dataset to HDFS...')
LOCAL_DATASET_PATH = "data/nf-data-application.csv"
HDFS_DATASET_PATH = cf_helpers.upload_to_hdfs(ARGS.api_url, CF_INFO.org,
                                            '{}/{}'.format(PROJECT_DIR, LOCAL_DATASET_PATH),
                                            'nf-data-application')

LOGGER.info('Setting environment variables...')
cf_cli.set_env(ARGS.app_name, "FILE", HDFS_DATASET_PATH)

LOGGER.info('Starting application...')
cf_cli.start(ARGS.app_name)
