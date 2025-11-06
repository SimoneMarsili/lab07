package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, this.bankAccount.getBalance());
        assertEquals(0, this.bankAccount.getTransactionsCount());
        assertEquals(this.mRossi, this.bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        this.bankAccount.deposit(this.bankAccount.getAccountHolder().getUserID(), 100);
        assertEquals(this.bankAccount.getBalance(), 100);
        assertEquals(this.bankAccount.getTransactionsCount(), 1);
        this.bankAccount.chargeManagementFees(this.bankAccount.getAccountHolder().getUserID());
        assertEquals(this.bankAccount.getBalance(), 100-5.1);
        assertEquals(this.bankAccount.getTransactionsCount(), 0);
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            this.bankAccount.withdraw(this.bankAccount.getAccountHolder().getUserID(), -100);;
            Assertions.fail("Withdrawing a negative amount was possible, but should have thrown an exception");
        } catch (final IllegalArgumentException e) {
            assertEquals(0, bankAccount.getBalance());
            assertEquals(this.bankAccount.getTransactionsCount(), 0);
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try {
            this.bankAccount.withdraw(this.bankAccount.getAccountHolder().getUserID(), 1);;
            Assertions.fail("Withdrawing more money than it is in the account was possible, but should have thrown an exception");
        } catch (final IllegalArgumentException e) {
            assertEquals(0, bankAccount.getBalance());
            assertEquals(this.bankAccount.getTransactionsCount(), 0);
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }
}
