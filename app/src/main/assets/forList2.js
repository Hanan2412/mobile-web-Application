var array = [];
var array1 = [];
function createJson2() {
    array = ["1","2","3","4"];
    array1 = ["11","22","33","44"];
    let a = "21";

    let jsonObject = [];
    for(let i =0;i<array.length;i++)
    {
        let item = {};
        item["country"] = array[i];
        item["random1"] = array1[i];
        jsonObject[i] = item;
    }
    console.log(jsonObject);
    return jsonObject;
}