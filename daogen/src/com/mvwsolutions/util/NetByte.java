package com.mvwsolutions.util;

/**
 * Methods for dealing with byte arrays that contain integral values in network
 * byte-order, you might obtain from network communication protocols or Java
 * class files.
 * 
 * @author Sergey Mineyev
 */

public final class NetByte {

	/**
	 * Makes an unsigned int value out of a byte
	 * 
	 * @param b
	 *            byte value to make unsigned
	 */
	public static final int mU(byte b) {
		int retval = b;
		if (retval < 0) {
			retval += 256;
		}
		return retval;
	}

	/**
	 * Writes the least significant 16 bits of an int to a pair of bytes, in
	 * network byte order.
	 * 
	 * @param value
	 *            Integer value to write into the byte array
	 * @param array
	 *            The byte array to receive the value
	 * @param offset
	 *            Position within the byte array that the first byte of the
	 *            value should be written
	 */
	public static final void intToPair(int value, byte[] array, int offset) {
		array[offset + 1] = (byte) (value & 0xff);
		value >>>= 8;
		array[offset] = (byte) value;
	}

	/**
	 * Writes an integer to four bytes, in network byte order.
	 * 
	 * @param value
	 *            Integer value to write into the byte array
	 * @param array
	 *            The byte array to receive the value
	 * @param offset
	 *            Position within the byte array that the first byte of the
	 *            value should be written
	 */
	public static final void intToQuad(int value, byte[] array, int offset) {
		array[offset + 3] = (byte) (value & 0xff);
		value >>>= 8;
		array[offset + 2] = (byte) (value & 0xff);
		value >>>= 8;
		array[offset + 1] = (byte) (value & 0xff);
		value >>>= 8;
		array[offset] = (byte) value;
	}

	/**
	 * Interprets a pair of bytes as a 16-bit integer in network byte order.
	 * 
	 * @param array
	 *            Byte array that contains bytes to be interpreted as integer
	 *            value
	 * @param offset
	 *            Position within array of the first byte to be interpreted
	 */
	public static final int pairToInt(byte[] array, int offset) {
		return (array[offset] << 8) | mU(array[offset + 1]);
	}

	/**
	 * Interprets a sequence of four bytes as an integer in network byte order.
	 * 
	 * @param array
	 *            Byte array that contains bytes to be interpreted as integer
	 *            value
	 * @param offset
	 *            Position within array of the first byte to be interpreted
	 */
	public static final int quadToInt(byte[] array, int offset) {
		return (array[offset] << 24) | (mU(array[offset + 1]) << 16)
				| (mU(array[offset + 2]) << 8) | mU(array[offset + 3]);
	}
}
