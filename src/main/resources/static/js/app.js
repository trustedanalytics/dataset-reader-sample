/**
 * Copyright (c) 2015 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
(function(){

    var IN_BYTES = 'ln_ibyt_SUM';
    var OUT_BYTES = 'ln_obyt_SUM';
    var DEGREES = 'ln_degree';
    var W_DEGREES = 'ln_weighted_degree';

    var keys = [
        IN_BYTES,
        OUT_BYTES,
        DEGREES,
        W_DEGREES
    ];

    function getHistogram(values) {
        var min = _.min(values);
        var max = _.max(values);
        var grouped = _.groupBy(values, function(value){
            return Math.floor(value);
        });

        var histogram = [];
        for(var i = Math.floor(min); i < max; i ++) {
            histogram.push([i, _.size(grouped[i])]);
        }
        return histogram;
    }

    function getScatter(values1, values2) {
        return _.zip(values1, values2);
    }

    function drawHistogram(key, data) {
        $('#' + key).plot([data], {
            series: {
                bars: {
                    show: true,
                    align: "left"
                }
            },
            xaxis: {
                mode: "categories",
                tickLength: 0
            }
        });
    }

    function drawScatter(key, data) {
        $('#' + key).plot([data], {
            series: {
                points: {
                    symbol: "cross",
                    radius: 2,
                    show: true,
                    fill: true,
                    lineWidth: 1
                },
                color: "#058DC7"
            }
        });
    }

    function draw(data) {
        _.each(keys, function(key1) {
            _.each(keys, function(key2) {
                if(key1 === key2) {
                    drawHistogram('histogram_' + key1, getHistogram(data[key1]));
                } else {
                    drawScatter('scatter_' + key2 + '_' + key1, getScatter(data[key1], data[key2]));
                }
            });
        });
    }

    $.get('/rest/parsed-dataset', {}, function(data) {
        var header = data[0];
        data = data.slice(1);

        var values = {};
        _.each(keys, function(key) {
            values[key] = _.pluck(data, header.indexOf(key))
                .map(function(value) {
                    return parseFloat(value);
                });
        });

        draw(values);

    });
})();
