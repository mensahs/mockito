/*
 * Copyright (c) 2007 Mockito contributors 
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.verification;

import org.mockito.exceptions.Exceptions;
import org.mockito.exceptions.parents.HasStackTrace;
import org.mockito.internal.invocation.InvocationMatcher;
import org.mockito.internal.invocation.InvocationsCalculator;
import org.mockito.internal.progress.VerificationMode;

public class NumberOfInvocationsVerifier implements Verifier {

    public void verify(InvocationsCalculator calculator, InvocationMatcher wanted, VerificationMode mode) {
        if (mode.atLeastOnceMode()) {
            return;
        }
        
        int actualCount = calculator.countActual(wanted);
        Integer wantedCount = mode.wantedCount();
        
        if (actualCount < wantedCount) {
            HasStackTrace lastInvocation = calculator.getLastInvocationStackTrace(wanted);
            Exceptions.tooLittleActualInvocations(wantedCount, actualCount, wanted.toString(), lastInvocation);
        } else if (actualCount > wantedCount) {
            HasStackTrace firstUndesired = calculator.getFirstUndesiredInvocationStackTrace(wanted, mode);
            Exceptions.tooManyActualInvocations(wantedCount, actualCount, wanted.toString(), firstUndesired);
        }
    }
}
