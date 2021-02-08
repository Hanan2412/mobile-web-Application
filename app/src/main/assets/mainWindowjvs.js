
var dataTable = [];
var size;
var total = 0;
function fillArray(name,cost,quantity,date) {

    console.log([date,name,cost,quantity]);

    console.log([typeof(date),typeof(name),typeof(cost),typeof(quantity)]);

    window.JSInterface.newTransaction(date,name,parseFloat(cost),parseInt(quantity,10));
}

function getDataTable(){

    if(dataTable.length < 1){

        size = window.JSInterface.setupTransactionTable();

        for(var i = 1; i < size ; i++){
            dataTable.push(getTransaction(i));
        }
        dataTable.push(getTransaction(0));
        return dataTable;
    }
    else return dataTable;
}

function getTransaction(index){

    var date = window.JSInterface.getTransactionDate(index);
    var product = window.JSInterface.getTransactionProduct(index);
    var price = window.JSInterface.getTransactionPrice(index);
    var quantity = window.JSInterface.getTransactionQuantity(index);
    if(index!==0)
    {
    total = total + price*quantity;
    }
    else
    {
    price = price - total;
    }
    var dataRow = [date,product,price,quantity];

    return dataRow;
}
function setBudget(date,budget)
{
    console.log("this is budget");
    console.log(budget);
    window.JSInterface.newTransaction(date,"budget",parseFloat(budget),1);
}

function readBudget()
{
    let budget = window.JSInterface.readBudget();
    return budget;
}
function deleteRow(){
    window.JSInterface.deleteRowFromDB(parseInt(document.getElementById("rowNum").value));
    console.log("row deleted!");
}

