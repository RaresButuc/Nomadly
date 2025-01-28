import styles from "./page.module.css";

import Navbar from "./components/navbar";

export default function Home() {
  return (
    <>
      <Navbar />
      <div className={styles.page}>
        <main className={styles.main}>
        <video className="video-bg" src="/video/bg1.mp4" autoPlay loop muted  style={{width:"100%"}}/>
        </main>
      </div>
    </>
  );
}
