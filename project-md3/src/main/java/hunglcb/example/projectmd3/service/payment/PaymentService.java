package hunglcb.example.projectmd3.service.payment;

import hunglcb.example.projectmd3.repository.payment.IPaymentRepository;
import hunglcb.example.projectmd3.repository.payment.PaymentRepository;

public class PaymentService implements IPaymentService {

    private final IPaymentRepository paymentRepository;

    public PaymentService() {
        this.paymentRepository = new PaymentRepository();
    }

    @Override
    public Integer findMethodIdByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        return paymentRepository.findIdByName(name.trim());
    }
}


