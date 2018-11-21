/**
 *  Pellet Stove Thermostat
 *
 *  Copyright 2018 Kyle Roberts
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Pellet Stove Thermostat",
    namespace: "Testudoed",
    author: "Kyle Roberts",
    description: "Pellet Stove Thermostat",
    category: "Convenience",
    iconUrl: "https://www.osburn-mfg.com/remote.axd/sbiweb.blob.core.windows.net/media/3883/3000_op00030_product.jpg?mode=crop?mode=crop&amp;width=660&amp;height=525",
    iconX2Url: "https://www.osburn-mfg.com/remote.axd/sbiweb.blob.core.windows.net/media/3883/3000_op00030_product.jpg?mode=crop?mode=crop&amp;width=660&amp;height=525",
    iconX3Url: "https://www.osburn-mfg.com/remote.axd/sbiweb.blob.core.windows.net/media/3883/3000_op00030_product.jpg?mode=crop?mode=crop&amp;width=660&amp;height=525")


preferences {
	section("Choose a temperature sensor... "){
		input "sensor", "capability.temperatureMeasurement", title: "Sensor"
	}
	section("Select the pellet stove outlet(s)... "){
		input "outlets", "capability.switch", title: "Outlets", multiple: true
	}
	section("Set the desired temperature..."){
		input "setpoint", "decimal", title: "Set Temp"
	}
    section("Set the desired temperature delta..."){
		input "delta", "decimal", title: "Set Temp Delta"
	}
}

def installed()
{
	subscribe(sensor, "temperature", temperatureHandler)
}

def updated()
{
	unsubscribe()
	subscribe(sensor, "temperature", temperatureHandler)
}

def temperatureHandler(evt)
{
    if (desiredTemp - currentTemp >= delta) 
    {
        outlets.on()
    }
    else if (currentTemp == desiredTemp)
    {
        outlets.off()
    }
}

