var fs = require('fs'),
    readLineSync = require('readline-sync'),
    axios = require('axios');

const URL = 'https://a.data.osmbuildings.org/0.2/anonymous/tile/', // /{{ZOOM}}/{{X}}/{{Y}}.json
      ENCODING = 'utf8';

var userInput = [],
    menuText = ['input zoom level => ', 'input x, y coordinate (ex => 1032,9991)', 'save fileName => '];

module.exports = {

    initApp : function () {
        this.getUserInput();
        let url = this.getOsmURL();
        let header = {'Access-Control-Allow-Origin' : '*'};
        this.sendHttpRequest(url, header);
    },
    getOsmURL : function () {
        let coordinate = userInput[1].split(',');
        return URL + userInput[0] + '/' +coordinate[0] + '/' + coordinate[1] + '.json';
    },
    getUserInput : function()  {
        // get user input text
        for(let consoleLine of menuText) {
            userInput.push(readLineSync.question(consoleLine));
        }  
    },
    sendHttpRequest : function (url, header) {
        axios.get(url, {
            headers : header
        }).then(response => {
            debugger;
        }).catch(err => {
            console.log(err);
        });
    },
    saveJsonFile : function (jsonText, name, path) {
        fs.writeFile(path + '/' +name, JSON.stringify(jsonText, null, '\t'), ENCODING, (err) => {
            if(err) {
                console.log(err);
            }
            console.log( name + ' json file save.');
        });
    },
    checkFileName : function (path) {
        fs.access(path, fs.F_OK, (err) => {
            if(err) {
                return false; 
            } else {
                return true;
            }
        });
    },
}

