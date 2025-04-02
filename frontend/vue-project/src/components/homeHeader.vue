<template>
  <header>
    <div class="d-flex aline-items-center">
      <div class="container">
        <div class="d-flex align-items-center">


          <!-- Nút mở menu trên màn hình nhỏ -->
          <div class="d-flex d-lg-none toggle-menu-mobile align-items-center ">
            <button type="button" data-bs-toggle="offcanvas" data-bs-target="#menu-collapse">
              <i class="fa-solid fa-sliders"></i>
            </button>

            <div class="search">
              <a href="product" class="icon-btn">
                <i class="fa-solid fa-magnifying-glass"></i>
              </a>
            </div>
          </div>
          <!-- Offcanvas Sidebar -->
          <div class="offcanvas offcanvas-start" id="menu-collapse">
            <div class="offcanvas-header">
              <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas">

              </button>
            </div>
            <div class="offcanvas-body">
              <ul class="navbar-nav mx-auto">
                <li class="nav-item">
                  <a class="nav-link" href="#">HOME</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#">PRODUCT</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#">COLLECTION</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#">SALE</a>
                </li>
              </ul>
            </div>
          </div>

          <!-- Logo -->
          <div class="d-flex align-items-center justify-content-center justify-content-lg-start col-logo ">
            <a href="#">Weekend</a>

          </div>


          <!-- Menu chính -->
          <div class="d-none d-lg-flex col-menu">
            <nav class="navbar-nav d-none d-lg-block ">
              <ul class=" nav mx-auto">
                <li class="nav-item">
                  <a class="nav-link" href="#">HOME</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#">PRODUCT</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#">COLLECTION</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#">SALE</a>
                </li>
              </ul>
            </nav>
          </div>
          <!-- Các icon tài khoản, yêu thích, giỏ hàng -->
          <div class="d-flex align-items-center gap-3">
            <a href="product" class="icon-btn d-none d-lg-flex">
              <i class="fa-solid fa-magnifying-glass"></i>
            </a>

            <button type="button" class="btn" data-bs-toggle="modal" data-bs-target="#login">
              <i class="fa-regular fa-user"></i>
            </button>
            <!-- login -->
            <div class="modal fade" id="login" style="display: none;" aria-hidden="true">
              <div class="modal-dialog modal-dialog--login modal-dialog-centered" role="document">
                <div class="modal-content">
                  <div class="modal-header p-3">
                    <button type="button" class="btn-close" data-bs-dismiss="modal">

                    </button>
                  </div>
                  <div class="modal-body p-5">
                    <div class="text-center py-2 px-2">
                      <div class="form-success hide" id="ResetSuccess">
                        We've sent you an email with a link to update your password.
                      </div>
                      <div id="CustomerLoginForm" class="form-vertical">
                        <form method="post" action="/account/login" id="customer_login" accept-charset="UTF-8"
                          data-login-with-shop-sign-in="true"><input type="hidden" name="form_type"
                            value="customer_login"><input type="hidden" name="utf8" value="✓">
                          <h3 class="mb-3">Login</h3>
                          <p class="mb-5">Please enter your e-mail and password: </p>


                          <div class="form-group mb-4">
                            <input type="email" name="customer[email]" id="CustomerEmail" class="form-control "
                              placeholder="Email" autocorrect="off" autocapitalize="off" autofocus="true">
                            <label for="CustomerEmail" class="label--hidden">Email</label>
                          </div>
                          <div class="form-group mb-4">
                            <input type="password" value="" name="customer[password]" id="CustomerPassword"
                              class="form-control " placeholder="Password">
                            <label for="CustomerPassword" class="label--hidden">Password</label>
                          </div>
                          <p class="text-right mb-3 small"><a href="#recover" id="RecoverPassword">Forgot your
                              password?</a></p>
                          <p>
                            <button type="submit" class="btn btn-theme gradient-theme w-100">Login</button>
                          </p>
                          <p class="pt-2"><span class="d-inline-block pr-2">New customer? </span><a
                              href="https://shopify.com/71465173218/account?locale=en&amp;region_country=AU"
                              id="customer_register_link" target="_blank">Register</a></p>
                        </form>
                      </div>
                      <div id="RecoverPasswordForm" class="hide">
                        <h3>Reset your password</h3>
                        <p class="mb-4">We will send you an email to reset your password.</p>
                        <div class="form-vertical">
                          <form method="post" action="/account/recover" accept-charset="UTF-8"><input type="hidden"
                              name="form_type" value="recover_customer_password"><input type="hidden" name="utf8"
                              value="✓">
                            <div class="form-group mb-4">
                              <input class="form-control" type="email" value="" name="email" id="RecoverEmail"
                                placeholder="Email" autocorrect="off" autocapitalize="off">
                              <label for="RecoverEmail" class="label--hidden">Email</label>
                            </div>
                            <p>
                              <button type="submit" class="btn btn-theme gradient-theme w-100">Submit</button>
                            </p>
                            <button type="button" id="HideRecoverPasswordLink" class="text-link link-accent-color">Back
                              to login</button>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

            </div>

            <a href="#" class="icon-btn position-relative">
              <i class="fa-regular fa-heart"></i>
              <span class="badge">1</span>
            </a>
            <a href="#" class="icon-btn position-relative">
              <i class="fa-solid fa-cart-shopping"></i>
              <span class="badge">3</span>
            </a>
          </div>

        </div>
      </div>
    </div>
  </header>
</template>

<style>
/* Bóng nhẹ cho header */
header {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  padding: 15px 0;
  font-family: "Roboto", sans-serif;
  font-weight: 600;
  color: #333;
}

/* Navbar */
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

/* Logo */
.navbar-brand {
  font-size: 26px;
  font-weight: bold;
}


/* Menu link */
.navbar-nav .nav-link {
  font-size: 18px;
  margin: 0 12px;
  transition: 0.3s;
  color: #333;
}

.navbar-nav .nav-link:hover {
  color: #b32d3d;
  text-decoration: underline;
}

/* Icon buttons */
.icon-btn {
  font-size: 20px;
  color: #333;
  text-decoration: none;
  position: relative;
  padding: 5px 10px;
}

.icon-btn:hover {
  color: #b32d3d;
}

/* Badge (số lượng sản phẩm) */
.badge {
  position: absolute;
  top: -5px;
  right: -5px;
  background: #b32d3d;
  color: white;
  font-size: 12px;
  font-weight: bold;
  padding: 3px 7px;
  border-radius: 50%;
}


.col-logo {
  flex: 1;
  /* Cho phép logo chiếm phần không gian hợp lý */
  text-align: center;
}

.col-menu {
  flex: 2;
  /* Menu chiếm không gian lớn hơn */
}

.label--hidden {
  visibility: hidden;
  transition: all 2s;
}
</style>