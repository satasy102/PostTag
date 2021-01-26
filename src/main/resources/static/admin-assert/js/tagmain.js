var tagData = [];
var tags = {} || tags;

tags.intTable = function () {
    $("#datatables").DataTable({
        ajax: {
            url: '/api/tags/',
            method: "GET",
            datatype: "json",
            dataSrc: ""
        },
        columns: [
            {
                data: "tag_id", name: "ID", title: "ID", orderable: true
            },
            {
                data: "name", name: "Name", title: "Tên", orderable: true
            },
            {
                data: "tag_id", name: "Action", title: "Thao tác", sortable: false,
                orderable: false, "render": function (data) {
                    var str = "<a href='javascript:' title='Sửa tag' onclick='tags.get(" + data + ")' data-toggle=\"modal\" data-target=\"#modalAddEdit\" style='color: orange'><i class=\"fas fa-edit\"></i></a> " +
                        "<a href='javascript:' title='Xóa tag' onclick='tags.delete(" + data + ")' style='color: red'><i class=\"fas fa-trash-alt\"></i></a>"
                    return str;
                }
            }
        ]
    });
};

tags.addNew = function () {
    $('#modalTitle').html("Thêm Tag mới");
    tags.resetForm();
    $('#modalAddEdit').modal('show');
};

tags.resetForm = function () {
    $('#formAddEdit')[0].reset();
    $('#name').val('');
}

tags.get = function (id) {
    console.log('get :' + id);
    $.ajax({
        url: "/api/tag/" + id,
        method: "GET",
        dataType: "json"
    }).done(function (data) {
        $('#formAddEdit')[0].reset();
        $('#modalTitle').html("Chỉnh sửa thông tin Tag");
        $('#tag_id').val(data.tag_id);
        $('#name').val(data.name);
        $('#modalAddEdit').modal('show');
    }).fail(function () {
        toastr.info('Lấy không thành công', 'INFORMATION:')
    })
}


tags.save = function () {
    if ($("#formAddEdit").valid()) {
        if ($('#tag_id').val() == 0) {
            var tagObj = {};
            tagObj.name = $('#name').val();
            //
            $.ajax({
                url: "/api/tag/",
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(tagObj)
            }).done(function (data) {
                console.log("POST success");
                $('#modalAddEdit').modal('hide');
                $("#datatables").DataTable().ajax.reload();
                toastr.info('Thêm mới thành công', 'INFORMATION:')
            }).fail(function () {
                toastr.error('Thêm mới không thành công', 'INFORMATION:')
            })
        } else {
            var tagObj = {};
            tagObj.name = $('#name').val();
            tagObj.tag_id = $('#tag_id').val();
            //
            $.ajax({
                url: "/api/tag/",
                method: "PUT",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(tagObj)
            }).done(function (data) {
                $('#modalAddEdit').modal('hide');
                $("#datatables").DataTable().ajax.reload();
                toastr.info('Cập nhật thành công', 'INFORMATION:')
            }).fail(function () {
                toastr.error('Cập nhật không thành công', 'INFORMATION:')
            })
        }
    }
    return false;
}

tags.delete = function (id) {
    bootbox.confirm({
        message: "Bạn có muốn xóa Tag này không?",
        buttons: {
            confirm: {
                label: 'Có',
                className: 'btn-success'
            },
            cancel: {
                label: 'Không',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result) {
                $.ajax({
                    url: "/api/tag/" + id,
                    method: "DELETE",
                    dataType: "json"
                }).done(function () {
                    console.log("DELETE SUCCESS");
                    $("#datatables").DataTable().ajax.reload();
                    toastr.info('Xóa thành công!', 'INFORMATION:')
                }).fail(function (jqXHR, exception) {
                    if(jqXHR.status != 200)
                    toastr.error('Xóa không thành công!', 'INFORMATION:')
                })
            }
        }
    })
};

tags.initValidation = function () {
    $("#formAddEdit").validate({
        rules: {
            name: {
                required: true,
                maxlength: 150
            }
        },
        messages: {
            name: {
                required: "Vui lòng nhập Tên Tag",
                maxlength: "Chỉ được nhập tối đa 150 ký tự"
            }
        }
    });
}


$(document).ready(function () {
    tags.intTable();
    tags.initValidation();
});
