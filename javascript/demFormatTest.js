// DEM file format create and child creater
// this module is create for testing
// DO NOT USE ANYWERE.

const ArraySize = 65,
      MaxValue = 10,
      MinValue = 0;

var arrayBuffer = [];

module.exports = {
    initAppHandler : function () {
        arrayBuffer = this.createArray();
        console.log('arrayBuffer => ' + arrayBuffer.length);
        let result = this.getCuttingArray(3, arrayBuffer);
        console.log('cutArray => ' + result.length);
        let childArrayTile = this.createChildTile(result, 33);
        console.log(childArrayTile.length);
        let childArray = this.calculateValue(childArrayTile);
        console.log(childArray.length);
    },
    calculateValue : function (sourceArray) {
        const centerValueCal = (num1, num2) => (num1 + num2) / 2;
        let forloopIndex = 0,
            forloopFlag = false;
        let childArray = [];
        for(let item of sourceArray) {
            if(forloopIndex % ArraySize == 0) {
                // content calculate flag
                forloopFlag = !forloopFlag;
            }
            if(forloopFlag) {
                // content array calculate
                if(forloopIndex % 2 == 0 && ((forloopIndex % (ArraySize -1)) != 0) || forloopIndex == 0 || forloopIndex == 4160) {
                    let centerValue = centerValueCal(item, sourceArray[forloopIndex + 2]) 
                    childArray.push(item, centerValue);
                } else if ((forloopIndex % (ArraySize -1)) == 0) {
                    // content array last item input
                    childArray.push(item);
                    // console.log('number : ' + Math.floor(forloopIndex / ArraySize) + '    push item => ' + item + '     loopIndex => ' + forloopIndex);
                }
            } else {
                // zero array calculate
                let aheadArrayNumberIndex = (forloopIndex - ArraySize) + (forloopIndex % ArraySize),
                    backArrayNumberIndex = (forloopIndex + ArraySize) + (forloopIndex % ArraySize);
                let centerValue = centerValueCal(sourceArray[aheadArrayNumberIndex], sourceArray[backArrayNumberIndex]);
                childArray.push(centerValue);
            }
            forloopIndex++;
        }
        return new Float32Array(childArray);
    },
    createArray : function () {
        // create Array [ ..., ...]
        // connect =>  y * 65 + x 
        const thisApp = this;
        let tempArray = [];
        for(let i = 0; i < ArraySize * ArraySize; i++) {
            let value = thisApp.createRandomValue();
            tempArray.push(value);
        }
        
        let array65 = new Float32Array(tempArray);
        return array65;
    },
    createChildTile : function (targetArray, sliceNum) {
        //create child array tile
        let childArrayTile = [],
            tempArray = [],
            //create emptyArray
            emptyArray = new Array(ArraySize).fill(0),
            arraySliceLength = targetArray.length / sliceNum;
        //slice array
        for(let i = 0; i < arraySliceLength; i++) {
            tempArray.push(targetArray.slice(i * arraySliceLength, (i+1) * arraySliceLength));
        }
        //push zero value
        let forloopIndex = 1;
        for(let itemArray of tempArray) {
            let itemForloopIndex = 1;
            for(let item of itemArray) {
                childArrayTile.push(item);
                if(itemForloopIndex % sliceNum != 0) {
                    // add 0 value between dem Value 
                    childArrayTile.push(0);
                }
                itemForloopIndex++;
            }
            // zero array push
            if(forloopIndex % sliceNum != 0){
                childArrayTile.push.apply(childArrayTile, emptyArray);
            }
            forloopIndex++;
        }
        return new Float32Array(childArrayTile);
    },
    createRandomValue : function () {
        //return random value. format => 00.00 || vender => 0 ~ 10
        //create random int value
        let value = Math.floor(Math.random() * (MaxValue - MinValue + 1)) + MinValue;
        //create decimal value
        let decimalValue = Number(Math.random().toFixed(2));

        return Number((value + decimalValue).toFixed(2));
    },
    getCuttingArray : function (floorValue, targetArray) {
        // slice Array and return 
        // 0 = (x : 33 ~ 65, y : 0 ~ 33)
        // 1 = (x : 33 ~ 65, y : 33 ~ 65)
        // 2 = (x : 0 ~ 33, y : 0 ~ 33)
        // 3 = (x : 33 ~ 65, y : 33 ~ 65)

        // center point
        let centerValue = Math.round(ArraySize/2);
        let centerPoint = ArraySize * centerValue;

        let sliceXArray,
            sliceYArray = [];

        // check floor and cut point create 
        let flagX = (floorValue < 2 ? true : false),
            flagY = (floorValue % 2 == 0 ? true : false);
        
        //slice X array
        if(flagX) {
            //floor 0, 1
            sliceXArray = targetArray.slice(0, centerPoint);
        } else {
            //floor 2, 3
            sliceXArray = targetArray.slice(centerPoint - ArraySize);
        }
        // console.log('slice X array ' + sliceXArray.length + '      Number : ' + floorValue);
        //slice Y array
        for(let indexYNum = 0; indexYNum < centerValue; indexYNum++) {
            for(let indexXNum = (flagY ? 0 : centerValue); indexXNum <= (flagY ? centerValue - 1 : ArraySize); indexXNum++){
                let point = centerValue * indexYNum + indexXNum;
                sliceYArray.push(sliceXArray[point]);
            }
        }
        return new Float32Array(sliceYArray);
    }
}