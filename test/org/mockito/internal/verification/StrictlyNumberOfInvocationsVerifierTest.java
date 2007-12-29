package org.mockito.internal.verification;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.mockito.internal.progress.VerificationModeImpl.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.RequiresValidState;
import org.mockito.exceptions.Reporter;
import org.mockito.exceptions.base.HasStackTrace;
import org.mockito.internal.invocation.Invocation;
import org.mockito.internal.invocation.InvocationBuilder;
import org.mockito.internal.invocation.InvocationMatcher;
import org.mockito.internal.progress.VerificationModeBuilder;

public class StrictlyNumberOfInvocationsVerifierTest extends RequiresValidState {

    private StrictlyNumberOfInvocationsVerifier verifier;
    private ReporterStub reporterStub;
    private InvocationMatcher wanted;
    private LinkedList<Invocation> invocations;
    private InvocationsFinderStub finderStub;
    
    @Before
    public void setup() {
        reporterStub = new ReporterStub();
        finderStub = new InvocationsFinderStub();
        verifier = new StrictlyNumberOfInvocationsVerifier(finderStub, reporterStub);
        
        wanted = new InvocationBuilder().toInvocationMatcher();
        invocations = new LinkedList<Invocation>(asList(new InvocationBuilder().toInvocation()));
    }
    
    @Test
    public void shouldNeverVerifyIfModeIsNotStrict() throws Exception {
        verifier.verify(null, wanted, atLeastOnce());
    }
    
    @Test
    public void shouldPassIfWantedIsZeroAndFirstUnverifiedChunkIsEmpty() throws Exception {
        assertTrue(finderStub.firstUnverifiedChunkToReturn.isEmpty());
        verifier.verify(invocations, wanted, new VerificationModeBuilder().times(0).strict());
    }
    
    @Test
    public void shouldPassIfWantedIsZeroAndFirstUnverifiedChunkDoesNotMatch() throws Exception {
        Invocation differentMethod = new InvocationBuilder().differentMethod().toInvocation();
        finderStub.firstUnverifiedChunkToReturn.add(differentMethod); 
        
        assertFalse(wanted.matches(differentMethod));
        verifier.verify(invocations, wanted, new VerificationModeBuilder().times(0).strict());
    }
    
    @Test
    public void shouldReportTooLittleInvocations() throws Exception {
        Invocation first = new InvocationBuilder().toInvocation();
        Invocation second = new InvocationBuilder().toInvocation();
        finderStub.firstUnverifiedChunkToReturn.addAll(asList(first, second)); 
        
        verifier.verify(invocations, wanted, new VerificationModeBuilder().times(4).strict());
        
        assertEquals(4, reporterStub.wantedCount);
        assertEquals(2, reporterStub.actualCount);
        assertSame(second.getStackTrace(), reporterStub.lastActualStackTrace);
        assertEquals(wanted.toString(), reporterStub.wanted);
    }
    
    @Test
    public void shouldReportTooManyInvocations() throws Exception {
        Invocation first = new InvocationBuilder().toInvocation();
        Invocation second = new InvocationBuilder().toInvocation();
        finderStub.firstUnverifiedChunkToReturn.addAll(asList(first, second)); 
        
        verifier.verify(invocations, wanted, new VerificationModeBuilder().times(1).strict());
        
        assertEquals(1, reporterStub.wantedCount);
        assertEquals(2, reporterStub.actualCount);
        assertSame(second.getStackTrace(), reporterStub.firstUndesired);
        assertEquals(wanted.toString(), reporterStub.wanted);
    }
    
    @Test
    public void shouldMarkInvocationsAsVerified() throws Exception {
        Invocation invocation = new InvocationBuilder().toInvocation();
        finderStub.firstUnverifiedChunkToReturn.add(invocation);
        assertFalse(invocation.isVerifiedStrictly());
        
        verifier.verify(invocations, wanted, new VerificationModeBuilder().times(1).strict());
        
        assertTrue(invocation.isVerifiedStrictly());
    }

    class ReporterStub extends Reporter {
        private HasStackTrace lastActualStackTrace;
        private int actualCount;
        private int wantedCount;
        private HasStackTrace firstUndesired;
        private String wanted;

        @Override public void strictlyTooLittleActualInvocations(int wantedCount, int actualCount, String wanted, HasStackTrace lastActualStackTrace) {
            this.wantedCount = wantedCount;
            this.actualCount = actualCount;
            this.wanted = wanted;
            this.lastActualStackTrace = lastActualStackTrace;
        }
        
        @Override public void strictlyTooManyActualInvocations(int wantedCount, int actualCount, String wanted, HasStackTrace firstUndesired) {
            this.wantedCount = wantedCount;
            this.actualCount = actualCount;
            this.wanted = wanted;
            this.firstUndesired = firstUndesired;
        }
    }
}
