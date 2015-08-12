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

    function getHistogram(data, key) {
        var values = _.pluck(data, key).map(function(value){
            return parseFloat(value);
        });
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

    function getScatter(data, key1, key2) {
        var values1 = _.pluck(data, key1).map(function(value){
            return parseFloat(value);
        });

        var values2 = _.pluck(data, key2).map(function(value){
            return parseFloat(value);
        });

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

    $.get('/rest/parsed-dataset', {}, function(data) {
        var header = data[0];
        data = data.slice(1);

        var inBytesKey = header.indexOf('ln_ibyt_SUM');
        var outBytesKey = header.indexOf('ln_obyt_SUM');
        var degreeKey = header.indexOf('ln_degree');
        var weightedDegreeKey = header.indexOf('ln_weighted_degree');

        drawHistogram('histogram_inbytes', getHistogram(data, inBytesKey));
        drawHistogram('histogram_outbytes', getHistogram(data, outBytesKey));
        drawHistogram('histogram_degree', getHistogram(data, degreeKey));
        drawHistogram('histogram_weighted_degree', getHistogram(data, weightedDegreeKey));

        drawScatter('scatter_inbytes_outbytes', getScatter(data, inBytesKey, outBytesKey));
        drawScatter('scatter_inbytes_degree', getScatter(data, inBytesKey, degreeKey));
        drawScatter('scatter_inbytes_weighted_degree', getScatter(data, inBytesKey, weightedDegreeKey));

        drawScatter('scatter_outbytes_inbytes', getScatter(data, outBytesKey, inBytesKey));
        drawScatter('scatter_outbytes_degree', getScatter(data, outBytesKey, degreeKey));
        drawScatter('scatter_outbytes_weighted_degree', getScatter(data, outBytesKey, weightedDegreeKey));

        drawScatter('scatter_degree_inbytes', getScatter(data, degreeKey, inBytesKey));
        drawScatter('scatter_degree_outbytes', getScatter(data, degreeKey, outBytesKey));
        drawScatter('scatter_degree_weighted_degree', getScatter(data, degreeKey, weightedDegreeKey));

        drawScatter('scatter_weighted_degree_inbytes', getScatter(data, weightedDegreeKey, inBytesKey));
        drawScatter('scatter_weighted_degree_outbytes', getScatter(data, weightedDegreeKey, outBytesKey));
        drawScatter('scatter_weighted_degree_degree', getScatter(data, weightedDegreeKey, degreeKey));

    });
})();
