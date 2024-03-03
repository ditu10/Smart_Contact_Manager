console.log("hello world!")

const toggleSidebar = () => {
    let sidebar = document.getElementById("main-sidebar");

    sidebar.classList.toggle("sidebar-t");
}



$('#myTable').DataTable( {
    select: true
});


const deleteContact = (contactId) => {
    Swal.fire({
        title: "Are you sure to delete this contact?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = "/user/delete?contactId=" + contactId;
        } else {
            swal("Your contact is safe!!")
        }
    });
}