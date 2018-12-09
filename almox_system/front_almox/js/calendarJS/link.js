$('#data').datepicker({
    uiLibrary: 'bootstrap4'
});

var getData;
function pegaData() {
    getData = document.getElementById('data').value;
    console.log(getData);
    return (tratarData(getData));
}

function tratarData(getData) {
    var dataTratada = getData;

    var ano = dataTratada.substr(6, 9);
    var mes = dataTratada.substr(3, 2);
    var dia = dataTratada.substr(0, 2);

    var dataTratada = ano + "-" + mes + "-" + dia;

    return (dataTratada);
}
