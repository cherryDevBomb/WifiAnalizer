package analyzer.model.frame;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FrameBody {

    private FixedParameters fixedParameters;
    private List<InformationElement> informationElements;

    public static FrameBody parse(byte[] bytes) {
        return FrameBody.builder()
                .fixedParameters(FixedParameters.parse(Arrays.copyOfRange(bytes, 0, 12)))
                .informationElements(InformationElement.parseList(Arrays.copyOfRange(bytes, 12, bytes.length)))
                .build();
    }

    public Optional<InformationElement> getInformationElementById(InformationElementID elementID) {
        return informationElements.stream()
                .filter(element -> elementID.getValue() == element.getElementId())
                .findFirst();
    }
}
