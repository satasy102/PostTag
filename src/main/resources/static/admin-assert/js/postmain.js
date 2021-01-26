var posts = {} || posts;
var tagData = [];
var tags = {} || tags;

//POST-JS
posts.initTags = function () {
    $.ajax({
        url: "/api/tags/",
        method: "GET",
        dataType: "json",
        success: function (data) {
            tagData = data;
            $('#tag').empty();
            $.each(data, function (i, v) {
                $('#tag').append(
                    `<option value='${v.tag_id}'>${v.name}</option>`
                );
            });
            $('.tag').select2({width: 'resolve'});
        }
    });
};

posts.intTable = function () {
    $("#datatables").DataTable({
        ajax: {
            url: '/api/posts/',
            method: "GET",
            datatype: "json",
            dataSrc: ""
        },
        columns: [
            {
                data: "post_id", name: "ID", title: "ID", orderable: true
            },
            {
                data: "title", name: "Title", title: "Tiêu đề", orderable: true
            },
            {
                data: "description", name: "Description", title: "Tóm tắt nội dung", sortable: false,
                orderable: false
            },
            {
                data: "postedAt", name: "Posted at", title: "Ngày post", sortable: true
            },
            {
                data: "lastUpdatedAt", name: "Last Updated at", title: "Ngày cập nhật", sortable: true
            },
            {
                data: "tags", name: "Tag", title: "Thẻ Tag", "render": function (data) {
                    var str = "";
                    data.map(e => {
                        str += `<button class="btn btn-outline-info btn-sm" type='button'>${e.name} <br></button>`
                    })
                    return str;
                }
            },
            {
                data: "post_id", name: "Action", title: "Thao tác", sortable: false,
                orderable: false, "render": function (data) {
                    var str = "<a href='javascript:' title='Sửa post' onclick='posts.get(" + data + ")' data-toggle=\"modal\" data-target=\"#modalAddEdit\" style='color: orange'><i class=\"fas fa-edit\"></i></a> " +
                        "<a href='javascript:' title='Thêm post' onclick='posts.delete(" + data + ")' style='color: red'><i class=\"fas fa-trash-alt\"></i></a>"
                    return str;
                }
            }
        ]
    });
};

posts.addNew = function () {
    $('#modalTitle').html("Thêm Bài mới");
    posts.resetForm();
    $('#modalAddEdit').modal('show');
}

posts.resetForm = function () {
    $('#formAddEdit')[0].reset();
    $('#title').val('');
    $('#description').val('');
    $('#content').val('');
    $('.tag').val(null).trigger('change');



}


posts.get = function (id) {
    console.log('get :' + id);
    $.ajax({
        url: "/api/post/" + id,
        method: "GET",
        dataType: "json"
    }).done(function (data) {
        $('#formAddEdit')[0].reset();
        $('#modalTitle').html("Chỉnh sửa Bài viết");
        $('#post_id').val(data.post_id);
        $('#title').val(data.title);
        $('#description').val(data.description);
        $('#content').val(data.content);
        var tags = [];
        data.tags.forEach(e => tags.push(e.tag_id));
        $('.tag').val(tags).trigger('change');
        $('#modalAddEdit').modal('show');
    }).fail(function () {
        toastr.error('Lấy dữ liệu bị lỗi', 'INFORMATION:')
    });
}

posts.save = function () {
    if ($("#formAddEdit").valid()) {
        if ($('#post_id').val() == 0) {
            var postObj = {};
            postObj.title = $('#title').val();
            postObj.description = $('#description').val();
            postObj.content = $('#content').val();
            //
            var tag_id = $('.tag').val();
            var tags = [];
            tag_id.forEach(e => {
                var tag = {};
                tag.tag_id = e;
                tags.push(tag);
            })
            postObj.tags = tags;

            $.ajax({
                url: "/api/post/",
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(postObj)
            }).done(function (data) {
                $('#modalAddEdit').modal('hide');
                $("#datatables").DataTable().ajax.reload();
                toastr.info('Thêm thành công', 'INFORMATION:')
            }).fail(function () {
                console.log("POST ");
                $('#modalAddEdit').modal('hide');
                $("#datatables").DataTable().ajax.reload();
                toastr.error('Thêm không thành công', 'INFORMATION:')

            });
        } else {
            var postObj = {};
            postObj.title = $('#title').val();
            postObj.description = $('#description').val();
            postObj.content = $('#content').val();
            postObj.post_id = $('#post_id').val();
            //
            var tag_id = $('.tag').val();
            var tags = [];
            tag_id.forEach(e => {
                var tag = {};
                tag.tag_id = e;
                tags.push(tag);
            })
            postObj.tags = tags;
            $.ajax({
                url: "/api/post/",
                method: "PUT",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(postObj)
            }).done(function (data) {
                $('#modalAddEdit').modal('hide');
                $("#datatables").DataTable().ajax.reload();
                toastr.info('Cập nhật thành công', 'INFORMATION:')
            }).fail(function () {
                console.log("POST ");
                $('#modalAddEdit').modal('hide');
                $("#datatables").DataTable().ajax.reload();
                toastr.error('Cập nhật không thành công', 'INFORMATION:')

            });
        }
        return false;
    }
}
posts.delete = function (id) {
    bootbox.confirm({
        message: "Bạn có muốn xóa bài viết này không?",
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
                    url: "/api/post/" + id,
                    method: "DELETE",
                    dataType: "json"
                }).done(function () {
                    console.log("DELETE SUCCESS");
                    $("#datatables").DataTable().ajax.reload();
                    toastr.info('Xóa thành công!', 'INFORMATION:')
                }).fail(function () {
                    toastr.error('Xóa không thành công!', 'INFORMATION:')
                });
            }
        }
    })
}


posts.initValidation = function () {
    $("#formAddEdit").validate({
        rules: {
            title: {
                required: true,
                maxlength: 150
            },
            description: {
                required: true,
                maxlength: 250
            },
            content: {
                required: true
            },
            tags: "required"
        },
        messages: {
            title: {
                required: "Vui lòng nhập Tiêu đề",
                maxlength: 150
            },
            description: {
                required: "Vui lòng nhập Tóm tắt bài viết",
                maxlength: 250
            },
            content: {
                required: "Vui lòng nhập Nội dung"
            },
            tags: "Vui lòng gắn thẻ Tag!!!",
        }
    });
}

posts.init = function () {
    posts.intTable();
    posts.initValidation();
    posts.initTags();
}

$(document).ready(function () {
    posts.init();
});
