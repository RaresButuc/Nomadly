export default function Footer() {
  return (
    <div
      className="container-xl"
      style={{
        position: "fixed",
        bottom: 0,
        left: "50%",
        transform: "translateX(-50%)",
      }}
    >
      <footer className="d-flex flex-wrap justify-content-between align-items-center py-3 my-2 border-top border-warning-subtle">
        <div className="col-md-4 d-flex align-items-center">
          <a
            href="/"
            className="mb-3 me-2 mb-md-0 text-body-secondary text-decoration-none lh-1"
          >
            <img
              src="photos/logo.png"
              alt="Nomadly.com"
              className="h-auto"
              style={{ width: 155 }}
            />{" "}
          </a>
          <span className="mb-3 mb-md-0 text-warning">
            &copy; 2023 Company, Inc
          </span>
        </div>

        <ul className="nav col-md-4 justify-content-end list-unstyled d-flex">
          <li className="ms-3">
            <a className="text-body-secondary" href="#">
              <img
                src="photos/social-media-icons/facebook.png"
                alt="facebookLogo"
                className="h-auto"
                style={{ width: 35 }}
              />
            </a>
          </li>
          <li className="ms-3">
            <a className="text-body-secondary" href="#">
              <img
                src="photos/social-media-icons/instagram.png"
                alt="facebookLogo"
                className="h-auto"
                style={{ width: 35 }}
              />
            </a>
          </li>
          <li className="ms-3">
            <a className="text-body-secondary" href="#">
              <img
                src="photos/social-media-icons/x.png"
                alt="facebookLogo"
                className="h-auto"
                style={{ width: 35 }}
              />
            </a>
          </li>
        </ul>
      </footer>
    </div>
  );
}
