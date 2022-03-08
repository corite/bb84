fun main() {
    val n = 64
    val k = 8
    val bb84 = BB84()
//Alice
    val a1 = bb84.getRandomBits(n)
    val a2 = bb84.getRandomBits(n)
    println("a-value bits: ${a1.toStr()}")
    println("a-basis bits: ${a2.toStr()}")

    val aQBits = bb84.encodeQBits(a1, a2)
//Bob
    val b2 = bb84.getRandomBits(n)
    val b1 = bb84.measureQBits(aQBits, b2)
    println("b-basis bits: ${b2.toStr()}")
    println("b-value bits: ${b1.toStr()}")
//Both
    val commonBitIndices = bb84.getCommonBitIndices(a2, b2)
    val compIndices = bb84.getRandomIndices(k, commonBitIndices.size)
    println("common bit indices: ${commonBitIndices.toStr()}")
    println("key-indices to compare: ${compIndices.toStr()}")

//Alice
    val aKeyBits = bb84.getBitsAtIndices(a1, commonBitIndices)
    val aCompBits = bb84.getBitsAtIndices(aKeyBits, compIndices)
//Bob
    val bKeyBits = bb84.getBitsAtIndices(b1, commonBitIndices)
    val bCompBits = bb84.getBitsAtIndices(bKeyBits, compIndices)
//Both
    println("compare-bits are equal: ${aCompBits.contentEquals(bCompBits)}")
    println("a-key: ${aKeyBits.toStr()}")
    println("b-key: ${bKeyBits.toStr()}")
}

private fun ByteArray.toStr():String {
    return this.joinToString (", "){ it.toString(2) }
}
private fun IntArray.toStr():String {
    return this.joinToString (", "){ it.toString(10) }
}