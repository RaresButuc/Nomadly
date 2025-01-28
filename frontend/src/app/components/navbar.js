export default function Navbar() {
  return (
    <nav className="navbar navbar-custom fixed-top border-bottom navbar-expand-lg">
      <div className="container-xl">
        <a className="navbar-brand" href="/">
          <img
            src="/photos/logo.png"
            alt="ourLogo"
            className="h-auto"
            style={{ maxWidth: 175 }}
          />
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNavDropdown"
          aria-controls="navbarNavDropdown"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNavDropdown">
          <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <a
                className="nav-link active mx-3"
                aria-current="page"
                href="#"
                style={{ color: "white" }}
              >
                <b>HOME</b>
              </a>
            </li>
            <li className="nav-item">
              <a
                className="nav-link active mx-3"
                href="#"
                style={{ color: "white" }}
              >
                <b>FIND A DESTINATION</b>
              </a>
            </li>
            <li className="nav-item navbar-join-button">
              <a className="btn btn-warning" href="#" style={{color:"white"}}>
                <b>JOIN US</b>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
