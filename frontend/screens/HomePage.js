import { Button } from "react-native-paper";

import { Text, Image, ImageBackground, View } from "react-native";
import { StyleSheet } from "react-native";

export default function HomePage() {
  return (
    <ImageBackground
      source={require("../assets/home-page-bg.jpg")}
      style={styles.container}
      fadeDuration={1000}
    >
      <View>
        <Image
          source={require("../assets/logo.png")}
          style={styles.logo}
          fadeDuration={1000}
        />

        <Button
          icon="airplane-marker"
          mode="contained"
          buttonColor="#e56e38"
          labelStyle={{ fontSize: 20 }}
          style={styles.startButton}
        >
          Get Started NOW
        </Button>
      </View>
      <Button style={styles.alreadyMemberLink} textColor="#ffffff">
        Already A Member? Log in HERE
      </Button>
    </ImageBackground>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  logo: {
    alignItems: "center",
    justifyContent: "center",
    bottom: "30%",
    width: "330",
    height: "330",
  },
  startButton: {
    padding: "10",
    bottom: "22%",
  },
  alreadyMemberLink: {
    position: "absolute",
    bottom: 20,
    textAlign: "center",
    borderWidth: 2,
    borderColor: "#ffffff",
    padding: "2",
  },
});
