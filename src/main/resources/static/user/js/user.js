$(document).ready(function(){
    $('#myTabs a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
    $('.delete').click(function () {
        $(this).parent().parent().hide();
    });
    $('.delete-wish').click(function () {
        $(this).parent().parent().parent().hide();
    });
});

