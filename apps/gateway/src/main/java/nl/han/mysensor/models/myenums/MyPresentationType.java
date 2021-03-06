package nl.han.mysensor.models.myenums;

import nl.han.gateway.exceptions.NotFoundException;

/**
 * MySensors presentation states, more info:
 * https://www.mysensors.org/download/serial_api_20#presentation
 *
 * @author Thomas
 * @since 0.1
 */
public enum MyPresentationType {
    S_DOOR(0), //	Door and window sensors	                V_TRIPPED, V_ARMED
    S_MOTION(1), //	Motion sensors	                        V_TRIPPED, V_ARMED
    S_SMOKE(2), //	Smoke sensor	                        V_TRIPPED, V_ARMED
    S_BINARY(3), //	Binary device (on/off)	                V_STATUS, V_WATT
    S_DIMMER(4), //	Dimmable device of some kind	        V_STATUS (on/off), V_PERCENTAGE (dimmer level 0-100), V_WATT
    S_COVER(5), //	Window covers or shades	                V_UP, V_DOWN, V_STOP, V_PERCENTAGE
    S_TEMP(6), //	Temperature sensor	                    V_TEMP, V_ID
    S_HUM(7), //	Humidity sensor	                        V_HUM
    S_BARO(8), //	Barometer sensor (Pressure)	            V_PRESSURE, V_FORECAST
    S_WIND(9), //	Wind sensor	                            V_WIND, V_GUST, V_DIRECTION
    S_RAIN(10), //	Rain sensor	                            V_RAIN, V_RAINRATE
    S_UV(11), //	UV sensor	                            V_UV
    S_WEIGHT(12), //	Weight sensor for scales etc.	    V_WEIGHT, V_IMPEDANCE
    S_POWER(13), //	Power measuring device, like power meters   V_WATT, V_KWH, V_VAR, V_VA, V_POWER_FACTOR
    S_HEATER(14), //	Heater device	                    V_HVAC_SETPOINT_HEAT, V_HVAC_FLOW_STATE, V_TEMP, V_STATUS
    S_DISTANCE(15), //	Distance sensor	                    V_DISTANCE, V_UNIT_PREFIX
    S_LIGHT_LEVEL(16), //	Light sensor	                V_LIGHT_LEVEL (uncalibrated percentage), V_LEVEL (light level in lux)
    S_ARDUINO_NODE(17), //	Arduino node device
    S_ARDUINO_REPEATER_NODE(18), //	Arduino repeating node device
    S_LOCK(19), //	Lock device	                            V_LOCK_STATUS
    S_IR(20), //	Ir sender/receiver device	            V_IR_SEND, V_IR_RECEIVE, V_IR_RECORD
    S_WATER(21), //	Water meter	                            V_FLOW, V_VOLUME
    S_AIR_QUALITY(22), //	Air quality sensor e.g. MQ-2	V_LEVEL, V_UNIT_PREFIX
    S_CUSTOM(23), //	Use this for custom sensors where no other fits.
    S_DUST(24), //	Dust level sensor	                    V_LEVEL, V_UNIT_PREFIX
    S_SCENE_CONTROLLER(25), //	Scene controller device	    V_SCENE_ON, V_SCENE_OFF
    S_RGB_LIGHT(26), //	RGB light	                        V_RGB, V_WATT
    S_RGBW_LIGHT(27), //	RGBW light (with separate white component)
    // V_RGBW, V_WATT
    S_COLOR_SENSOR(28), //	Color sensor	                V_RGB
    S_HVAC(29), //	Thermostat/HVAC device	                V_STATUS, V_TEMP, V_HVAC_SETPOINT_HEAT, V_HVAC_SETPOINT_COOL,
    // V_HVAC_FLOW_STATE, V_HVAC_FLOW_MODE, V_HVAC_SPEED
    S_MULTIMETER(30), //	Multimeter device	            V_VOLTAGE, V_CURRENT, V_IMPEDANCE
    S_SPRINKLER(31), //	Sprinkler device	                V_STATUS (turn on/off), V_TRIPPED (if fire detecting device)
    S_WATER_LEAK(32), //	Water leak sensor	            V_TRIPPED, V_ARMED
    S_SOUND(33), //	Sound sensor	                        V_LEVEL (in dB), V_TRIPPED, V_ARMED
    S_VIBRATION(34), //	Vibration sensor	                V_LEVEL (vibration in Hz), V_TRIPPED, V_ARMED
    S_MOISTURE(35), //	Moisture sensor	                    V_LEVEL (water content or moisture in percentage?), V_TRIPPED, V_ARMED
    S_INFO(36), //	LCD text device	                        V_TEXT
    S_GAS(37), //	Gas meter	                            V_FLOW, V_VOLUME
    S_GPS(38), //	GPS Sensor	                            V_POSITION
    S_WATER_QUALITY(39); //	Water quality sensor	        V_TEMP, V_PH, V_ORP, V_EC, V_STATUS


    private final int value;

    MyPresentationType(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Search for presentation type by value
     *
     * @param searchValue
     * @return Presentation type
     * @throws NotFoundException
     */
    public static MyPresentationType getByValue(int searchValue) throws NotFoundException {
        for (MyPresentationType myPresentationType : MyPresentationType.values()) {
            if (myPresentationType.getValue() == searchValue) {
                return myPresentationType;
            }
        }
        throw new NotFoundException("Unkown presentation type");
    }
}
