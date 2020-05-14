import React, { useState } from 'react';
import { StyleSheet, Button, View, SafeAreaView, Text, Alert, TextInput } from 'react-native';
import Svg, { Circle, Rect } from 'react-native-svg';
import Constants from 'expo-constants';
import Canvas from 'react-native-canvas';

function Separator() {
  return <View style={styles.separator} />;
}

export default function App() {
  const [counter, outCounter] = useState('0');
  const [cel, celText] = useState('0');
  const [fahr, fahrText] = useState('0');
  let inCel = false
  let inFahr = false

  let celToFarh = (text) => {
    if (text == "" || inFahr)
      return;
    inCel = true;
    celText(text);
    fahrText((1.8 * parseFloat(text) + 32));
    inCel = false;
  }

  let fahrToCel = (text) => {
    if (text == "" || inCel)
      return;
    inFahr = true;
    fahrText(text);
    celText((0.5555 * (parseFloat(text) - 32)));
    inFahr = false;
  }

  handleCanvas = (canvas) => {
    const ctx = canvas.getContext('2d');
    ctx.fillStyle = 'purple';
    ctx.fillRect(0, 0, 100, 100);
  }
  
  
  return (
    <SafeAreaView style={styles.container}>
      <View>
        <Button title="Increase" onPress = {()=>outCounter((parseInt(counter) + 1).toString())} />
        <Text>{counter}</Text>
        <Button title="Decrease" onPress = {()=>outCounter((parseInt(counter) - 1).toString())} />
      </View>
      <Separator />
      <View>
        <TextInput
          style={{ height: 40, borderColor: 'gray', borderWidth: 1 }}
          onChangeText={text => celToFarh(text)}
          value={cel}
        />
        <TextInput
          style={{ height: 40, borderColor: 'gray', borderWidth: 1 }}
          onChangeText={text => fahrToCel(text) }
          value={fahr}
        />
      </View>
      <Separator />
      <View>
        <Svg height="300" width="300" viewBox="0 0 100 100" onTouchStart={()=>outCounter((parseInt(counter) + 1).toString())}>
          <Circle cx="50" cy="50" r="45" stroke="blue" strokeWidth="2.5" fill="green" />
          <Rect x="15" y="15" width="70" height="70" stroke="red" strokeWidth="2" fill="yellow" />
        </Svg>
      </View>
      <Separator />
        
      <View style={{ flex: 1, flexDirection: 'row' }}>
        <Canvas ref={this.handleCanvas}/>
      </View>
  </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: Constants.statusBarHeight,
    marginHorizontal: 16,
  },
  title: {
    textAlign: 'center',
    marginVertical: 8,
  },
  fixToText: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  separator: {
    marginVertical: 8,
    borderBottomColor: '#737373',
    borderBottomWidth: StyleSheet.hairlineWidth,
  },
  strokeColorButton: {
    marginHorizontal: 2.5, marginVertical: 8, width: 30, height: 30, borderRadius: 15,
  },
  strokeWidthButton: {
    marginHorizontal: 2.5, marginVertical: 8, width: 30, height: 30, borderRadius: 15,
    justifyContent: 'center', alignItems: 'center', backgroundColor: '#39579A'
  },
  functionButton: {
    marginHorizontal: 2.5, marginVertical: 8, height: 30, width: 60,
    backgroundColor: '#39579A', justifyContent: 'center', alignItems: 'center', borderRadius: 5,
  }
});