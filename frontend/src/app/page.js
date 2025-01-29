import styles from "./page.module.css";

import Navbar from "./components/navbar";

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
          <h1 className="fw-bold" style={{ color: "white" }}>
            Your Journey, <mark>Shared</mark>. Your World, <mark>Expanded</mark>
            .
          </h1>
          <div className="d-flex justify-content-center">
            <h5 className="fw-bold mb-3 lh-base" style={{ color: "white" }}>
              A platform where <mark>travel</mark> and <mark>social media</mark>{" "}
              meet to create authentic experiences.
            </h5>
          </div>

          <div className="container-xl d-flex justify-content-center">
            <a
              className="btn btn-outline-warning fw-bold p-3"
              href="/post-ads"
              style={{ color: "white" }}
            >
              Start Your Jorney HERE
            </a>
          </div>
        </main>
      </div>
    </>
  );
}
