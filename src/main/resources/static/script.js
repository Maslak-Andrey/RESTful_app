$(document).ready(getUsers());

//User Table
function getUsers() {
    $("#table").empty();
    $.ajax({
        type: 'GET',
        url: '/admin/list',
        timeout: 3000,
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) {
                $("#table").append($('<tr>').append(
                    $('<td>').text(user.id),
                    $('<td>').text(user.username),
                    $('<td>').text(user.password),
                    $('<td>').text(user.roles),
                    $('<td>').append("<button type='button' data-toggle='modal' class='btn-info btn'" +
                        "data-target='#editUserModal' data-user-id='" + user.id + "'>Edit</button>"),
                    $('<td>').append("<button type='button' data-toggle='modal' class='btn btn-danger'" +
                        "data-target='#deleteUserModal' data-user-id='" + user.id + "'>Delete</button>")
                    )
                );
            });
        }
    });
}
$('[href="#v-pills-admin"]').on('show.bs.tab', (e) => {
    // getUsers()
    location.reload();
})

//Edit form
$("#editUserModal").on('show.bs.modal', (e) => {
    let userId = $(e.relatedTarget).data("user-id");

    $.ajax({
        url: "/admin/" + userId,
        type: "GET",
        dataType: "json"
    }).done((msg) => {

        let user = JSON.parse(JSON.stringify(msg));

        $("#idEdit").empty().val(user.id);
        $("#usernameEdit").empty().val(user.username);
        $("#passwordEdit").empty().val(user.password);

    });
})
//after press Edit in Modal window
$("#buttonEditSubmit").on('click', (e) => {
    e.preventDefault();

    // let roleCheck
    // if ($("#roleUserEdit").prop('checked')){
    //     roles: [$("#roleUserEdit").val()]
    // } if ($("#roleAdminEdit").prop('checked')) {
    //     roles: [$("#roleAdminEdit").val()]
    // }

    let editUser = {
        id: $("#idEdit").val(),
        username: $("#usernameEdit").val(),
        password: $("#passwordEdit").val(),
        roles: [$("#roleUserEdit").val()]
    }

    $.ajax({
        url: "/admin",
        type: "PUT",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(editUser)
    }),

        $("#editUserModal").modal('hide'),
        location.reload(),
        getUsers();
})
//Delete form
$("#deleteUserModal").on('show.bs.modal', (e) => {
    let userId = $(e.relatedTarget).data("user-id");

    $.ajax({
        url: "/admin/" + userId,
        type: "GET",
        dataType: "json"
    }).done((msg) => {
        let user = JSON.parse(JSON.stringify(msg));

        $("#idInputDelete").empty().val(user.id);
        $("#usernameInputDelete").empty().val(user.username);
        $("#passwordInputDelete").empty().val(user.password);


        $("#buttonDeleteSubmit").on('click', (e) => {
            e.preventDefault();

            $.ajax({
                url: "/admin/" + userId,
                type: "DELETE",
                dataType: "text"
            }).done((deleteMsg) => {
                $("#deleteUserModal").modal('hide');
                location.reload();
                getUsers();
            })
        })
    });
})

$('[href="#newUser"]').on('show.bs.tab', (e) => {
    $(() => {
        $("#usernameInputNew").empty().val("");
        $("#passwordInputNew").empty().val("");
    })
})

$("#buttonInputNewSubmit").on('click', (e) => {
    e.preventDefault();

    let newUser = {
        username: $("#usernameInputNew").val(),
        password: $("#passwordInputNew").val(),
        roles: [$("#roleUserInputNew").val()]
    }

    $.ajax({
        url: "/admin",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(newUser)
    }),
        getUsers(),
        $('#AdminTabs a[href="#usersTable"]').tab('show'),
        location.reload();
})

$('[href="#v-pills-user"]').on('show.bs.tab', (e) => {
    $("#change-tabContent").hide(),
    getCurrent();
})

function getCurrent() {
    $.ajax({
        url: "/getUser",
        type: "GET",
        dataType: "json"
    }).done((msg) => {
        let user = JSON.parse(JSON.stringify(msg));
        $("#current-user-table tbody").empty().append(
            $("<tr>").append(
                $("<td>").text(user.id),
                $("<td>").text(user.username),
                $("<td>").text(user.password),
                $("<td>").text(user.roles)
            ));
    }).fail(() => {
        alert("Error Get All Users!")
    })
}