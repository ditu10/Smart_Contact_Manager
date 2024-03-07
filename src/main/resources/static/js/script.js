console.log("hello world!")

const toggleSidebar = () => {
    let sidebar = document.getElementById("main-sidebar");

    sidebar.classList.toggle("sidebar-t");
}

const searchHandler = () =>{
    let query = document.getElementById("search-contact").value;
    let search_result = document.getElementById("search-result");
    if(query.trim() === ""){
        search_result.style.display = "none";
    }else{
        console.log(query);

        let url = `http://localhost:8081/user/search?query=` + query.trim();
        fetch(url)
            .then(res=>
                res.json())
            .then(data=>{
                console.log(data);
                if(data){
                    let text = "<div class='list-group'>";
                    data.forEach(contact => {
                        text += `<a class="list-group-item list-group-item-action" href='http://localhost:8081/user/contact/${contact.id}'>${contact.name} </a>`
                    })
                    text += "</div>";

                    search_result.innerHTML = text;
                    search_result.style.display = "block";
                }

            })


    }
}


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