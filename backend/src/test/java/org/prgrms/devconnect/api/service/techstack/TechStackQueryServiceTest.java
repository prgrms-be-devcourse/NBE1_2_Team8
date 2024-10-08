package org.prgrms.devconnect.api.service.techstack;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.devconnect.api.controller.techstack.dto.response.TechStackResponseDto;
import org.prgrms.devconnect.common.exception.ExceptionCode;
import org.prgrms.devconnect.common.exception.techstack.TechStackException;
import org.prgrms.devconnect.domain.define.fixture.TechStackFixture;
import org.prgrms.devconnect.domain.define.techstack.entity.TechStack;
import org.prgrms.devconnect.domain.define.techstack.repository.TechStackRepository;

@ExtendWith(MockitoExtension.class)
class TechStackQueryServiceTest {

  @Mock
  private TechStackRepository techStackRepository;

  @InjectMocks
  private TechStackQueryService techStackQueryService;

  @DisplayName("유효한_아이디가_주어지면_기술스택을_반환한다")
  @Test
  void 유효한_아이디가_주어지면_기술스택을_반환한다() {
    // given
    Long techStackId = 1L;
    when(techStackRepository.findById(techStackId))
        .thenReturn(Optional.ofNullable(TechStackFixture.createTechStack()));

    // when
    TechStack techStack = techStackQueryService.getTechStackByIdOrThrow(techStackId);

    // then
    assertThat(techStack).isNotNull();
    verify(techStackRepository).findById((techStackId));
  }

  @DisplayName("유효하지않은_아이디가_주어지면_에러가_발생한다")
  @Test
  void 유효하지않은_아이디가_주어지면_에러가_발생한다() {
    // given
    Long techStackId = 100L;
    when(techStackRepository.findById(techStackId))
        .thenReturn(Optional.empty());

    // when // then
    Assertions.assertThatThrownBy(
            () -> techStackQueryService.getTechStackByIdOrThrow(techStackId)
        ).isInstanceOf(TechStackException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_TECH_STACK.getMessage());
  }

  @DisplayName("유효한_아이디_리스트가_주어지면_기술스택_리스트를_반환한다")
  @Test
  void 유효한_아이디_리스트가_주어지면_기술스택_리스트를_반환한다() {
    // given
    List<Long> techStackIds = List.of(1L, 2L);
    when(techStackRepository.findAllByTechStackIdIn(techStackIds))
        .thenReturn(
            List.of(TechStackFixture.createTechStack(), TechStackFixture.createTechStack()));

    // when
    List<TechStack> result = techStackQueryService.getTechStacksByIdsOrThrow(techStackIds);

    // then
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(2);
    verify(techStackRepository).findAllByTechStackIdIn(techStackIds);
  }

  @DisplayName("유효하지않는_아이디_리스트가_주어지면_에러가_발생한다")
  @Test
  void 유효하지않는_아이디_리스트가_주어지면_에러가_발생한다() {
    List<Long> techStackIds = List.of(1L, 2L, 100L);
    when(techStackRepository.findAllByTechStackIdIn(techStackIds))
        .thenReturn(
            List.of(TechStackFixture.createTechStack(), TechStackFixture.createTechStack()));

    // when // then
    Assertions.assertThatThrownBy(
            () -> techStackQueryService.getTechStacksByIdsOrThrow(techStackIds)
        ).isInstanceOf(TechStackException.class)
        .hasMessage(ExceptionCode.NOT_FOUND_TECH_STACK.getMessage());
  }

  @DisplayName("기술스택_전체_리스트를_조회할_수_있다")
  @Test
  void 기술스택_전체_리스트를_조회할_수_있다() {
    // given

    // when
    List<TechStackResponseDto> allTechStacks = techStackQueryService.getAllTechStacks();

    // then
    verify(techStackRepository, times(1)).findAll();
  }

}