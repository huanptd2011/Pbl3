 // Logout handler
 async function handleLogout(event) {
    event.preventDefault();
    const token = localStorage.getItem("authToken");
    try {
        const response = await fetch('http://localhost:8080/api/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                "Authorization": `Bearer ${token}`
            }
        }).then(res => {
            if (res.ok) {
                alert("Logout thành công!");
                localStorage.removeItem("authToken");
                window.location.href = "http://127.0.0.1:5500/src/main/resources/templates/HTML/home.html";
            } else {
                alert("Lỗi khi logout");
            }
        });
    } catch (error) {
        console.error('Error during logout:', error);
    }
}