import styles from "./page.module.css";

import Navbar from "./components/navbar";
import Footer from "./components/footer";

export default function Home() {
  return (
    <>
      <Navbar />
      <div className={styles.page}>
        <main className={styles.main}>
          <video
            className="video-bg"
            src="/video/bg1.mp4"
            autoPlay
            loop
            muted
            style={{ width: "100%" }}
          />
          <h1 className="fw-bold display-4" style={{ color: "white" }}>
            Your Journey, <mark>Shared</mark>. Your World, <mark>Expanded</mark>
            .
          </h1>
          <div className="d-flex justify-content-center">
            <h5 className="fw-bold mb-3 lh-base" style={{ color: "white" }}>
              A platform where <mark>Travel</mark> and <mark>Social Media</mark>{" "}
              meet to create authentic experiences.
            </h5>
          </div>
          <div className="container-xl d-flex justify-content-center row">
            <div className="col-xl-3 py-2">
              <a
                className="btn btn-outline-warning fw-bold p-3 "
                href="/post-ads"
                style={{ color: "white" }}
              >
                Start Your Journey HERE
              </a>
            </div>
            <div className="col-xl-3 py-2">
              <a
                className="btn btn-outline-primary fw-bold p-3"
                href="/how-to-guide"
                style={{ color: "white" }}
              >
                Short Introduction Guide
              </a>
            </div>
          </div>
        </main>
        <div style={{ marginTop: "auto" }}></div>
        <Footer />
      </div>
    </>
  );
}
