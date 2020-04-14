import React, { useState } from 'react';
import { StyleSheet, Button, View, SafeAreaView, Text, Alert, TextInput } from 'react-native';
import Constants from 'expo-constants';

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
  
  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.container}>
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
        <Text style={styles.title}>
          All interaction for the component are disabled.
        </Text>
        <Button
          title="Press me"
          disabled
          onPress={() => Alert.alert('Cannot press this one')}
        />
      </View>
      <Separator />
      <View>
      <Text style={styles.title}>
        This layout strategy lets the title define the width of the button.
      </Text>
      <View style={styles.fixToText}>
        <Button
          title="Left button"
          onPress={() => Alert.alert('Left button pressed')}
        />
        <Button
          title="Right button"
          onPress={() => Alert.alert('Right button pressed')}
        />
      </View>
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
});