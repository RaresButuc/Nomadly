import "./globals.css";
import Providers from "./Providers";
import "bootstrap/dist/css/bootstrap.min.css";
import { Geist, Geist_Mono } from "next/font/google";

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata = {
  title: "Nomadly",
  description: "Your Journey, Shared. Your World, Expanded.",
};

export default function RootLayout({ children }) {
  return (
    <Providers>
      <html lang="en">
        <body
          style={{
            minHeight: "100vh",
            display: "flex",
            flexDirection: "column",
          }}
        >
          <Navbar />
          <main style={{ flex: 1 }}>{children}</main>
          <Footer />
        </body>
      </html>
    </Providers>
  );
}
