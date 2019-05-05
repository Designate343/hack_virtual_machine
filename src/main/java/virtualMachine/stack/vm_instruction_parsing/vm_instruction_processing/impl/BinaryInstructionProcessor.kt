package virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.impl

import virtualMachine.stack.memory.GlobalVirtualMemory
import virtualMachine.stack.datawrappers.Word
import virtualMachine.stack.datawrappers.instruction.Commands
import virtualMachine.stack.datawrappers.instruction.Instruction
import virtualMachine.stack.memory.MemorySegments
import virtualMachine.stack.vm_instruction_parsing.vm_instruction_processing.InstructionProcessor
import java.lang.RuntimeException

class BinaryInstructionProcessor : InstructionProcessor {

    override fun processInstruction(instruction: Instruction, virtualMemory: GlobalVirtualMemory) {
        val latestHead: Word = virtualMemory.popStack()
        val secondFromHead : Word = virtualMemory.popStack()

        val result : Word = processComparison(instruction, latestHead, secondFromHead)
        virtualMemory.loadIntoMemory(result, virtualMemory.stackPointer, MemorySegments.GLOBAL_STACK)
        virtualMemory.pushToStack(result)
    }

    private fun processComparison(instruction: Instruction, latestHead: Word,
                                  secondFromHead: Word) : Word {
        return when (instruction.command) {
            Commands.BITWISE_EQUALS -> Word(secondFromHead == latestHead)
            Commands.BITWISE_AND -> secondFromHead.bitwizeAnd(latestHead)
            Commands.BITWISE_OR -> secondFromHead.bitwiseOr(latestHead)
            Commands.GREATER_THAN -> secondFromHead.greaterThan(latestHead)
            Commands.LESS_THAN -> secondFromHead.lessThan(latestHead)
            Commands.ADD -> secondFromHead.add16Bit(latestHead)
            Commands.SUBTRACT -> secondFromHead.subtract(latestHead)
            else -> throw RuntimeException("command not implemented yet")
        }
    }

}