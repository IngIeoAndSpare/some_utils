    /**
     * ex) format("'{0}: \"{1}\"'", "name", "value"); ==> 'name: "value"'
     */
    function format (value) {
      var args = Array.from(arguments).slice(1);
      return value.replace(/{(\d+)}/g, function(match, number) {
        let index = parseInt(number);
        return typeof args[index] != 'undefined' ? args[index] : match;
      });
    }


     function checkMac (inputMac, vender) {
        let check = /^(([A-Fa-f0-9]{2}[:]){5}[A-Fa-f0-9]{2}[,]?)+$/i.test(inputMac);
        if(!check) {
          //TODO : validation fail 
        }
      }

      function checkIp(inputIp) {
        let check = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(inputIp);
        if(!check) {
          alert('ip validtaion fail. please check ip');
          return false;
        }
        return true;
    }